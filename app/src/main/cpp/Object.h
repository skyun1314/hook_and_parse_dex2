
// Created by xiaobai on 2016/9/25.
//

#ifndef HELPTOOLCLIENT_OBJECT_H
#define HELPTOOLCLIENT_OBJECT_H
#include <stddef.h>
#include <cstdint>

typedef uint8_t             u1;
typedef uint16_t            u2;
typedef uint32_t            u4;
typedef uint64_t            u8;
typedef int8_t              s1;
typedef int16_t             s2;
typedef int32_t             s4;
typedef int64_t             s8;
/* fwd decl */
struct DataObject;
struct InitiatingLoaderList;
struct ClassObject;
struct StringObject;
struct ArrayObject;
struct Method;
struct ExceptionEntry;
struct LineNumEntry;
struct StaticField;
struct InstField;
struct Field;
struct RegisterMap;

struct Object;
union JValue
{
    u1 z;
    s1 b;
    u2 c;
    s2 s;
    s4 i;
    s8 j;
    float f;
    double d;
    Object* l;
};

typedef void (*DalvikBridgeFunc)(const u4* args, JValue* pResult,
                                 const Method* method, struct Thread* self);

enum AccessFlags
{
    ACC_MIRANDA = 0x8000, // method (internal to VM)
    JAVA_FLAGS_MASK = 0xffff, // bits set from Java sources (low 16)
};

typedef void (*DalvikNativeFunc)(const u4* args, JValue* pResult);

enum ClassFlags
{
    CLASS_ISFINALIZABLE = (1 << 31), // class/ancestor overrides finalize()
    CLASS_ISARRAY = (1 << 30), // class is a "[*"
    CLASS_ISOBJECTARRAY = (1 << 29), // class is a "[L*" or "[[*"
    CLASS_ISCLASS = (1 << 28), // class is *the* class Class

    CLASS_ISREFERENCE = (1 << 27), // class is a soft/weak/phantom ref
    // only ISREFERENCE is set --> soft
            CLASS_ISWEAKREFERENCE = (1 << 26), // class is a weak reference
    CLASS_ISFINALIZERREFERENCE = (1 << 25), // class is a finalizer reference
    CLASS_ISPHANTOMREFERENCE = (1 << 24), // class is a phantom reference

    CLASS_MULTIPLE_DEFS = (1 << 23), // DEX verifier: defs in multiple DEXs

    /* unlike the others, these can be present in the optimized DEX file */
            CLASS_ISOPTIMIZED = (1 << 17), // class may contain opt instrs
    CLASS_ISPREVERIFIED = (1 << 16), // class has been pre-verified
};
#define EXPECTED_FILE_FLAGS \
    (ACC_CLASS_MASK | CLASS_ISPREVERIFIED | CLASS_ISOPTIMIZED)


#define SET_CLASS_FLAG(clazz, flag) \
    do { (clazz)->accessFlags |= (flag); } while (0)

#define CLEAR_CLASS_FLAG(clazz, flag) \
    do { (clazz)->accessFlags &= ~(flag); } while (0)

#define IS_CLASS_FLAG_SET(clazz, flag) \
    (((clazz)->accessFlags & (flag)) != 0)

#define GET_CLASS_FLAG_GROUP(clazz, flags) \
    ((u4)((clazz)->accessFlags & (flags)))

enum MethodFlags
{
    METHOD_ISWRITABLE = (1 << 31), // the method's code is writable
};

#define SET_METHOD_FLAG(method, flag) \
    do { (method)->accessFlags |= (flag); } while (0)

#define CLEAR_METHOD_FLAG(method, flag) \
    do { (method)->accessFlags &= ~(flag); } while (0)

#define IS_METHOD_FLAG_SET(method, flag) \
    (((method)->accessFlags & (flag)) != 0)

#define GET_METHOD_FLAG_GROUP(method, flags) \
    ((u4)((method)->accessFlags & (flags)))

enum ClassStatus
{
    CLASS_ERROR = -1,

    CLASS_NOTREADY = 0,
    CLASS_IDX = 1, /* loaded, DEX idx in super or ifaces */
    CLASS_LOADED = 2, /* DEX idx values resolved */
    CLASS_RESOLVED = 3, /* part of linking */
    CLASS_VERIFYING = 4, /* in the process of being verified */
    CLASS_VERIFIED = 5, /* logically part of linking; done pre-init */
    CLASS_INITIALIZING = 6, /* class init in progress */
    CLASS_INITIALIZED = 7, /* ready to go */
};
#define CLASS_WALK_SUPER ((unsigned int)(3))
#define CLASS_SMALLEST_OFFSET (sizeof(struct Object))
#define CLASS_BITS_PER_WORD (sizeof(unsigned long int) * 8)
#define CLASS_OFFSET_ALIGNMENT 4
#define CLASS_HIGH_BIT ((unsigned int)1 << (CLASS_BITS_PER_WORD - 1))
#define _CLASS_BIT_NUMBER_FROM_OFFSET(byteOffset) \
    (((unsigned int)(byteOffset) - CLASS_SMALLEST_OFFSET) / \
     CLASS_OFFSET_ALIGNMENT)
#define CLASS_CAN_ENCODE_OFFSET(byteOffset) \
    (_CLASS_BIT_NUMBER_FROM_OFFSET(byteOffset) < CLASS_BITS_PER_WORD)
#define CLASS_BIT_FROM_OFFSET(byteOffset) \
    (CLASS_HIGH_BIT >> _CLASS_BIT_NUMBER_FROM_OFFSET(byteOffset))
#define CLASS_OFFSET_FROM_CLZ(rshift) \
    (((int)(rshift) * CLASS_OFFSET_ALIGNMENT) + CLASS_SMALLEST_OFFSET)

struct InterfaceEntry
{
    ClassObject* clazz;
    int* methodIndexArray;
};

struct Object
{
    ClassObject* clazz;
    u4 lock;
};

#define DVM_OBJECT_INIT(obj, clazz_) \
    dvmSetFieldObject(obj, OFFSETOF_MEMBER(Object, clazz), clazz_)

struct DataObject : Object
{
    u4 instanceData[1];
};
struct StringObject : Object
{
    u4 instanceData[1];
    int length() const;
    int utfLength() const;
    ArrayObject* array() const;
    const u2* chars() const;
};
struct ArrayObject : Object
{
    u4 length;
    u8 contents[1];
};

struct InitiatingLoaderList
{
    Object** initiatingLoaders;
    int initiatingLoaderCount;
};
struct Field
{
    ClassObject* clazz; /* class in which the field is declared */
    const char* name;
    const char* signature; /* e.g. "I", "[C", "Landroid/os/Debug;" */
    u4 accessFlags;
};


struct StaticField : Field
{
    JValue value; /* initially set from DEX for primitives */
};
struct InstField : Field
{
    int byteOffset;
};

#define CLASS_FIELD_SLOTS   4
enum PrimitiveType
{
    PRIM_NOT = 0, /* value is a reference type, not a primitive type */
    PRIM_VOID = 1,
    PRIM_BOOLEAN = 2,
    PRIM_BYTE = 3,
    PRIM_SHORT = 4,
    PRIM_CHAR = 5,
    PRIM_INT = 6,
    PRIM_LONG = 7,
    PRIM_FLOAT = 8,
    PRIM_DOUBLE = 9,
};
struct ClassObject : Object
{
    u4 instanceData[CLASS_FIELD_SLOTS];

    const char* descriptor;
    char* descriptorAlloc;
    u4 accessFlags;
    u4 serialNumber;

    void* pDvmDex;

    ClassStatus status;

    ClassObject* verifyErrorClass;

    u4 initThreadId;

    size_t objectSize;

    ClassObject* elementClass;
    int arrayDim;
    PrimitiveType primitiveType;

    ClassObject* super;

    Object* classLoader;

    InitiatingLoaderList initiatingLoaderList;

    int interfaceCount;
    ClassObject** interfaces;

    int directMethodCount;
    Method* directMethods;
    int virtualMethodCount;
    Method* virtualMethods;


    int vtableCount;
    Method** vtable;

    int iftableCount;
    InterfaceEntry* iftable;


    int ifviPoolCount;
    int* ifviPool;


    int ifieldCount;
    int ifieldRefCount; // number of fields that are object refs
    InstField* ifields;

    u4 refOffsets;

    /* source file name, if known */
    const char* sourceFile;

    int sfieldCount;
    StaticField sfields[]; /* MUST be last item */
};


struct DexProto
{
    const void * dexFile; /* file the idx refers to */
    u4 protoIdx; /* index into proto_ids table of dexFile */
};

struct Method
{
    /* the class we are a part of */
    ClassObject* clazz;
    u4 accessFlags;

    u2 methodIndex;

    u2 registersSize; /* ins + locals */
    u2 outsSize;
    u2 insSize;

    /* method name, e.g. "<init>" or "eatLunch" */
    const char* name;

    DexProto prototype;

    const char* shorty;

    const u2* insns; /* instructions, in memory-mapped .dex */


    int jniArgInfo;


    DalvikBridgeFunc nativeFunc;


    bool fastJni;


    bool noRef;


    bool shouldTrace;


    const RegisterMap* registerMap;

    /* set if method was called during method profiling */
    bool inProfile;
};
enum
{
    ACC_PUBLIC = 0x00000001, // class, field, method, ic
    ACC_PRIVATE = 0x00000002, // field, method, ic
    ACC_PROTECTED = 0x00000004, // field, method, ic
    ACC_STATIC = 0x00000008, // field, method, ic
    ACC_FINAL = 0x00000010, // class, field, method, ic
    ACC_SYNCHRONIZED = 0x00000020, // method (only allowed on natives)
    ACC_SUPER = 0x00000020, // class (not used in Dalvik)
    ACC_VOLATILE = 0x00000040, // field
    ACC_BRIDGE = 0x00000040, // method (1.5)
    ACC_TRANSIENT = 0x00000080, // field
    ACC_VARARGS = 0x00000080, // method (1.5)
    ACC_NATIVE = 0x00000100, // method
    ACC_INTERFACE = 0x00000200, // class, ic
    ACC_ABSTRACT = 0x00000400, // class, method, ic
    ACC_STRICT = 0x00000800, // method
    ACC_SYNTHETIC = 0x00001000, // field, method, ic
    ACC_ANNOTATION = 0x00002000, // class, ic (1.5)
    ACC_ENUM = 0x00004000, // class, field, ic (1.5)
    ACC_CONSTRUCTOR = 0x00010000, // method (Dalvik only)
    ACC_DECLARED_SYNCHRONIZED =
    0x00020000, // method (Dalvik only)
    ACC_CLASS_MASK =
    (ACC_PUBLIC | ACC_FINAL | ACC_INTERFACE | ACC_ABSTRACT
     | ACC_SYNTHETIC | ACC_ANNOTATION | ACC_ENUM),
    ACC_INNER_CLASS_MASK =
    (ACC_CLASS_MASK | ACC_PRIVATE | ACC_PROTECTED | ACC_STATIC),
    ACC_FIELD_MASK =
    (ACC_PUBLIC | ACC_PRIVATE | ACC_PROTECTED | ACC_STATIC | ACC_FINAL
     | ACC_VOLATILE | ACC_TRANSIENT | ACC_SYNTHETIC | ACC_ENUM),
    ACC_METHOD_MASK =
    (ACC_PUBLIC | ACC_PRIVATE | ACC_PROTECTED | ACC_STATIC | ACC_FINAL
     | ACC_SYNCHRONIZED | ACC_BRIDGE | ACC_VARARGS | ACC_NATIVE
     | ACC_ABSTRACT | ACC_STRICT | ACC_SYNTHETIC | ACC_CONSTRUCTOR
     | ACC_DECLARED_SYNCHRONIZED),
};
bool dvmIsPublicMethod(const Method* method)
{
    return (method->accessFlags & ACC_PUBLIC) != 0;
}

bool dvmIsPrivateMethod(const Method* method)
{
    return (method->accessFlags & ACC_PRIVATE) != 0;
}

bool dvmIsStaticMethod(const Method* method)
{
    return (method->accessFlags & ACC_STATIC) != 0;
}

bool dvmIsSynchronizedMethod(const Method* method)
{
    return (method->accessFlags & ACC_SYNCHRONIZED) != 0;
}

bool dvmIsDeclaredSynchronizedMethod(const Method* method)
{
    return (method->accessFlags & ACC_DECLARED_SYNCHRONIZED) != 0;
}

bool dvmIsFinalMethod(const Method* method)
{
    return (method->accessFlags & ACC_FINAL) != 0;
}

bool dvmIsNativeMethod(const Method* method)
{
    return (method->accessFlags & ACC_NATIVE) != 0;
}

bool dvmIsAbstractMethod(const Method* method)
{
    return (method->accessFlags & ACC_ABSTRACT) != 0;
}

bool dvmIsSyntheticMethod(const Method* method)
{
    return (method->accessFlags & ACC_SYNTHETIC) != 0;
}

bool dvmIsMirandaMethod(const Method* method)
{
    return (method->accessFlags & ACC_MIRANDA) != 0;
}

bool dvmIsConstructorMethod(const Method* method)
{
    return *method->name == '<';
}

/* Dalvik puts private, static, and constructors into non-virtual table */
bool dvmIsDirectMethod(const Method* method)
{
    return dvmIsPrivateMethod(method) ||
           dvmIsStaticMethod(method) ||
           dvmIsConstructorMethod(method);
}

/* Get whether the given method has associated bytecode. This is the
* case for methods which are neither native nor abstract. */
bool dvmIsBytecodeMethod(const Method* method)
{
    return (method->accessFlags & (ACC_NATIVE | ACC_ABSTRACT)) == 0;
}

bool dvmIsProtectedField(const Field* field)
{
    return (field->accessFlags & ACC_PROTECTED) != 0;
}

bool dvmIsStaticField(const Field* field)
{
    return (field->accessFlags & ACC_STATIC) != 0;
}

bool dvmIsFinalField(const Field* field)
{
    return (field->accessFlags & ACC_FINAL) != 0;
}

bool dvmIsVolatileField(const Field* field)
{
    return (field->accessFlags & ACC_VOLATILE) != 0;
}

bool dvmIsInterfaceClass(const ClassObject* clazz)
{
    return (clazz->accessFlags & ACC_INTERFACE) != 0;
}

bool dvmIsPublicClass(const ClassObject* clazz)
{
    return (clazz->accessFlags & ACC_PUBLIC) != 0;
}

bool dvmIsFinalClass(const ClassObject* clazz)
{
    return (clazz->accessFlags & ACC_FINAL) != 0;
}

bool dvmIsAbstractClass(const ClassObject* clazz)
{
    return (clazz->accessFlags & ACC_ABSTRACT) != 0;
}

bool dvmIsAnnotationClass(const ClassObject* clazz)
{
    return (clazz->accessFlags & ACC_ANNOTATION) != 0;
}

bool dvmIsPrimitiveClass(const ClassObject* clazz)
{
    return clazz->primitiveType != PRIM_NOT;
}

/* linked, here meaning prepared and resolved */
bool dvmIsClassLinked(const ClassObject* clazz)
{
    return clazz->status >= CLASS_RESOLVED;
}

/* has class been verified? */
bool dvmIsClassVerified(const ClassObject* clazz)
{
    return clazz->status >= CLASS_VERIFIED;
}




bool dvmIsClassInitialized(const ClassObject* clazz)
{
    return (clazz->status == CLASS_INITIALIZED);
}

/* annotation constants */
enum
{
    kDexVisibilityBuild = 0x00, /* annotation visibility */
    kDexVisibilityRuntime = 0x01,
    kDexVisibilitySystem = 0x02,

    kDexAnnotationByte = 0x00,
    kDexAnnotationShort = 0x02,
    kDexAnnotationChar = 0x03,
    kDexAnnotationInt = 0x04,
    kDexAnnotationLong = 0x06,
    kDexAnnotationFloat = 0x10,
    kDexAnnotationDouble = 0x11,
    kDexAnnotationString = 0x17,
    kDexAnnotationType = 0x18,
    kDexAnnotationField = 0x19,
    kDexAnnotationMethod = 0x1a,
    kDexAnnotationEnum = 0x1b,
    kDexAnnotationArray = 0x1c,
    kDexAnnotationAnnotation = 0x1d,
    kDexAnnotationNull = 0x1e,
    kDexAnnotationBoolean = 0x1f,

    kDexAnnotationValueTypeMask = 0x1f, /* low 5 bits */
    kDexAnnotationValueArgShift = 5,
};

/* map item type codes */
enum
{
    kDexTypeHeaderItem = 0x0000,
    kDexTypeStringIdItem = 0x0001,
    kDexTypeTypeIdItem = 0x0002,
    kDexTypeProtoIdItem = 0x0003,
    kDexTypeFieldIdItem = 0x0004,
    kDexTypeMethodIdItem = 0x0005,
    kDexTypeClassDefItem = 0x0006,
    kDexTypeMapList = 0x1000,
    kDexTypeTypeList = 0x1001,
    kDexTypeAnnotationSetRefList = 0x1002,
    kDexTypeAnnotationSetItem = 0x1003,
    kDexTypeClassDataItem = 0x2000,
    kDexTypeCodeItem = 0x2001,
    kDexTypeStringDataItem = 0x2002,
    kDexTypeDebugInfoItem = 0x2003,
    kDexTypeAnnotationItem = 0x2004,
    kDexTypeEncodedArrayItem = 0x2005,
    kDexTypeAnnotationsDirectoryItem = 0x2006,
};

/* auxillary data section chunk codes */
enum
{
    kDexChunkClassLookup = 0x434c4b50, /* CLKP */
    kDexChunkRegisterMaps = 0x524d4150, /* RMAP */

    kDexChunkEnd = 0x41454e44, /* AEND */
};

/* debug info opcodes and constants */
enum
{
    DBG_END_SEQUENCE = 0x00,
    DBG_ADVANCE_PC = 0x01,
    DBG_ADVANCE_LINE = 0x02,
    DBG_START_LOCAL = 0x03,
    DBG_START_LOCAL_EXTENDED = 0x04,
    DBG_END_LOCAL = 0x05,
    DBG_RESTART_LOCAL = 0x06,
    DBG_SET_PROLOGUE_END = 0x07,
    DBG_SET_EPILOGUE_BEGIN = 0x08,
    DBG_SET_FILE = 0x09,
    DBG_FIRST_SPECIAL = 0x0a,
    DBG_LINE_BASE = -4,
    DBG_LINE_RANGE = 15,
};
enum
{
    kSHA1DigestLen = 20,
    kSHA1DigestOutputLen = kSHA1DigestLen * 2 + 1
};


/*
* Direct-mapped "header_item" struct.
*/
struct DexHeader
{
    u1 magic[8]; /* includes version number */
    u4 checksum; /* adler32 checksum */
    u1 signature[kSHA1DigestLen]; /* SHA-1 hash */
    u4 fileSize; /* length of entire file */
    u4 headerSize; /* offset to start of next section */
    u4 endianTag;
    u4 linkSize;
    u4 linkOff;
    u4 mapOff;
    u4 stringIdsSize;
    u4 stringIdsOff;
    u4 typeIdsSize;
    u4 typeIdsOff;
    u4 protoIdsSize;
    u4 protoIdsOff;
    u4 fieldIdsSize;
    u4 fieldIdsOff;
    u4 methodIdsSize;
    u4 methodIdsOff;
    u4 classDefsSize;
    u4 classDefsOff;
    u4 dataSize;
    u4 dataOff;
};

/*
* Direct-mapped "map_item".
*/
struct DexMapItem
{
    u2 type; /* type code (see kDexType* above) */
    u2 unused;
    u4 size; /* count of items of the indicated type */
    u4 offset; /* file offset to the start of data */
};

/*
* Direct-mapped "map_list".
*/
struct DexMapList
{
    u4 size; /* #of entries in list */
    DexMapItem list[1]; /* entries */
};

/*
* Direct-mapped "string_id_item".
*/
struct DexStringId
{
    u4 stringDataOff; /* file offset to string_data_item */
};

/*
* Direct-mapped "type_id_item".
*/
struct DexTypeId
{
    u4 descriptorIdx; /* index into stringIds list for type descriptor */
};

/*
* Direct-mapped "field_id_item".
*/
struct DexFieldId
{
    u2 classIdx; /* index into typeIds list for defining class */
    u2 typeIdx; /* index into typeIds for field type */
    u4 nameIdx; /* index into stringIds for field name */
};

/*
* Direct-mapped "method_id_item".
*/
struct DexMethodId
{
    u2 classIdx; /* index into typeIds list for defining class */
    u2 protoIdx; /* index into protoIds for method prototype */
    u4 nameIdx; /* index into stringIds for method name */
};

/*
* Direct-mapped "proto_id_item".
*/
struct DexProtoId
{
    u4 shortyIdx; /* index into stringIds for shorty descriptor */
    u4 returnTypeIdx; /* index into typeIds list for return type */
    u4 parametersOff; /* file offset to type_list for parameter types */
};

/*
* Direct-mapped "class_def_item".
*/
struct DexClassDef
{
    u4 classIdx; /* index into typeIds for this class */
    u4 accessFlags;
    u4 superclassIdx; /* index into typeIds for superclass */
    u4 interfacesOff; /* file offset to DexTypeList */
    u4 sourceFileIdx; /* index into stringIds for source file name */
    u4 annotationsOff; /* file offset to annotations_directory_item */
    u4 classDataOff; /* file offset to class_data_item */
    u4 staticValuesOff; /* file offset to DexEncodedArray */
};

/*
* Direct-mapped "type_item".
*/
struct DexTypeItem
{
    u2 typeIdx; /* index into typeIds */
};

/*
* Direct-mapped "type_list".
*/
struct DexTypeList
{
    u4 size; /* #of entries in list */
    DexTypeItem list[1]; /* entries */
};

typedef struct DexMapId
{
    u2 type; /*Section type*/

    u2 unused; /*unused*/
    u4 size; /* section size*/
    u4 offset; /* section offset */
} DexMapId;

/*
* Direct-mapped "code_item".
*
* The "catches" table is used when throwing an exception,
* "debugInfo" is used when displaying an exception stack trace or
* debugging. An offset of zero indicates that there are no entries.
*/
struct DexCode
{
    u2 registersSize;
    u2 insSize;
    u2 outsSize;
    u2 triesSize;
    u4 debugInfoOff; /* file offset to debug info stream */
    u4 insnsSize; /* size of the insns array, in u2 units */
    u2 insns[1];
    /* followed by optional u2 padding */
    /* followed by try_item[triesSize] */
    /* followed by uleb128 handlersSize */
    /* followed by catch_handler_item[handlersSize] */
};

/*
* Direct-mapped "try_item".
*/
struct DexTry
{
    u4 startAddr; /* start address, in 16-bit code units */
    u2 insnCount; /* instruction count, in 16-bit code units */
    u2 handlerOff; /* offset in encoded handler data to handlers */
};

/*
* Link table.  Currently undefined.
*/
struct DexLink
{
    u1 bleargh;
};


/*
* Direct-mapped "annotations_directory_item".
*/
struct DexAnnotationsDirectoryItem
{
    u4 classAnnotationsOff; /* offset to DexAnnotationSetItem */
    u4 fieldsSize; /* count of DexFieldAnnotationsItem */
    u4 methodsSize; /* count of DexMethodAnnotationsItem */
    u4 parametersSize; /* count of DexParameterAnnotationsItem */
    /* followed by DexFieldAnnotationsItem[fieldsSize] */
    /* followed by DexMethodAnnotationsItem[methodsSize] */
    /* followed by DexParameterAnnotationsItem[parametersSize] */
};

/*
* Direct-mapped "field_annotations_item".
*/
struct DexFieldAnnotationsItem
{
    u4 fieldIdx;
    u4 annotationsOff; /* offset to DexAnnotationSetItem */
};

/*
* Direct-mapped "method_annotations_item".
*/
struct DexMethodAnnotationsItem
{
    u4 methodIdx;
    u4 annotationsOff; /* offset to DexAnnotationSetItem */
};

/*
* Direct-mapped "parameter_annotations_item".
*/
struct DexParameterAnnotationsItem
{
    u4 methodIdx;
    u4 annotationsOff; /* offset to DexAnotationSetRefList */
};

/*
* Direct-mapped "annotation_set_ref_item".
*/
struct DexAnnotationSetRefItem
{
    u4 annotationsOff; /* offset to DexAnnotationSetItem */
};

/*
* Direct-mapped "annotation_set_ref_list".
*/
struct DexAnnotationSetRefList
{
    u4 size;
    DexAnnotationSetRefItem list[1];
};

/*
* Direct-mapped "annotation_set_item".
*/
struct DexAnnotationSetItem
{
    u4 size;
    u4 entries[1]; /* offset to DexAnnotationItem */
};

/*
* Direct-mapped "annotation_item".
*
* NOTE: this structure is byte-aligned.
*/
struct DexAnnotationItem
{
    u1 visibility;
    u1 annotation[1]; /* data in encoded_annotation format */
};

/*
* Direct-mapped "encoded_array".
*
* NOTE: this structure is byte-aligned.
*/
struct DexEncodedArray
{
    u1 array[1]; /* data in encoded_array format */
};

/*
* Lookup table for classes.  It provides a mapping from class name to
* class definition.  Used by dexFindClass().
*
* We calculate this at DEX optimization time and embed it in the file so we
* don't need the same hash table in every VM.  This is slightly slower than
* a hash table with direct pointers to the items, but because it's shared
* there's less of a penalty for using a fairly sparse table.
*/
struct DexClassLookup
{
    int size; // total size, including "size"
    int numEntries; // size of table[]; always power of 2
    struct
    {
        u4 classDescriptorHash; // class descriptor hash code
        int classDescriptorOffset; // in bytes, from start of DEX
        int classDefOffset; // in bytes, from start of DEX
    } table[1];
};

/*
* Header added by DEX optimization pass.  Values are always written in
* local byte and structure padding.  The first field (magic + version)
* is guaranteed to be present and directly readable for all expected
* compiler configurations; the rest is version-dependent.
*
* Try to keep this simple and fixed-size.
*/
struct DexOptHeader
{
    u1 magic[8]; /* includes version number */

    u4 dexOffset; /* file offset of DEX header */
    u4 dexLength;
    u4 depsOffset; /* offset of optimized DEX dependency table */
    u4 depsLength;
    u4 optOffset; /* file offset of optimized data tables */
    u4 optLength;

    u4 flags; /* some info flags */
    u4 checksum; /* adler32 checksum covering deps/opt */

    /* pad for 64-bit alignment if necessary */
};

#define DEX_OPT_FLAG_BIG            (1<<1)  /* swapped to big-endian */

#define DEX_INTERFACE_CACHE_SIZE    128     /* must be power of 2 */

/*
* Structure representing a DEX file.
*
* Code should regard DexFile as opaque, using the API calls provided here
* to access specific structures.
*/
struct DexFile
{
    /* directly-mapped "opt" header */
    const DexOptHeader* pOptHeader;

    /* pointers to directly-mapped structs and arrays in base DEX */
    const DexHeader* pHeader;
    const DexStringId* pStringIds;
    const DexTypeId* pTypeIds;
    const DexFieldId* pFieldIds;
    const DexMethodId* pMethodIds;
    const DexProtoId* pProtoIds;
    const DexClassDef* pClassDefs;
    const DexLink* pLinkData;

    /*
    * These are mapped out of the "auxillary" section, and may not be
    * included in the file.
    */
    const DexClassLookup* pClassLookup;
    const void* pRegisterMapPool; // RegisterMapClassPool

    /* points to start of DEX file data */
    const u1* baseAddr;

    /* track memory overhead for auxillary structures */
    int overhead;

    /* additional app-specific data structures associated with the DEX */
    //void*               auxData;
};


struct MemMapping
{
    char* addr; /* start of data */
    size_t length; /* length of data */

    void* baseAddr; /* page-aligned base address */
    size_t baseLength; /* length of mapping */
};

struct DvmDex
{
    /* pointer to the DexFile we're associated with */
    DexFile* pDexFile;

    /* clone of pDexFile->pHeader (it's used frequently enough) */
    const DexHeader* pHeader;

    /* interned strings; parallel to "stringIds" */
    struct StringObject** pResStrings;

    /* resolved classes; parallel to "typeIds" */
    struct ClassObject** pResClasses;

    /* resolved methods; parallel to "methodIds" */
    struct Method** pResMethods;

    /* resolved instance fields; parallel to "fieldIds" */
    /* (this holds both InstField and StaticField) */
    struct Field** pResFields;

    /* interface method lookup cache */
    struct AtomicCache* pInterfaceCache;

    /* shared memory region with file contents */
    bool isMappedReadOnly;
    MemMapping memMap;

    jobject dex_object;

    /* lock ensuring mutual exclusion during updates */
    pthread_mutex_t modLock;
};

struct JarFile
{
    u4* Nocare[9];
    char* cacheFileName;
    DvmDex* pDvmDex;
};

struct RawDexFile
{
    char* cacheFileName;
    struct DvmDex* pDvmDex; //DvmDex*
};

struct DexOrJar
{
    char* fileName;
    bool isDex;
    bool okayToFree;
    RawDexFile* pRawDexFile;
    JarFile* pJarFile;
    u1* pDexMemory; // malloc()ed memory, if any
};
#endif //HELPTOOLCLIENT_OBJECT_H