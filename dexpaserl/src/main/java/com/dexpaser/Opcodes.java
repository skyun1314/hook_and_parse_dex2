package com.dexpaser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zk on 2017/6/15.
 */

public class Opcodes {

    public static enum Opcode {


        // BEGIN(libdex-opcode-enum); GENERATED AUTOMATICALLY BY opcode-gen
        OP_NOP(0x00),
        OP_MOVE(0x01),
        OP_MOVE_FROM16(0x02),
        OP_MOVE_16(0x03),
        OP_MOVE_WIDE(0x04),
        OP_MOVE_WIDE_FROM16(0x05),
        OP_MOVE_WIDE_16(0x06),
        OP_MOVE_OBJECT(0x07),
        OP_MOVE_OBJECT_FROM16(0x08),
        OP_MOVE_OBJECT_16(0x09),
        OP_MOVE_RESULT(0x0a),
        OP_MOVE_RESULT_WIDE(0x0b),
        OP_MOVE_RESULT_OBJECT(0x0c),
        OP_MOVE_EXCEPTION(0x0d),
        OP_RETURN_VOID(0x0e),
        OP_RETURN(0x0f),
        OP_RETURN_WIDE(0x10),
        OP_RETURN_OBJECT(0x11),
        OP_CONST_4(0x12),
        OP_CONST_16(0x13),
        OP_CONST(0x14),
        OP_CONST_HIGH16(0x15),
        OP_CONST_WIDE_16(0x16),
        OP_CONST_WIDE_32(0x17),
        OP_CONST_WIDE(0x18),
        OP_CONST_WIDE_HIGH16(0x19),
        OP_CONST_STRING(0x1a),
        OP_CONST_STRING_JUMBO(0x1b),
        OP_CONST_CLASS(0x1c),
        OP_MONITOR_ENTER(0x1d),
        OP_MONITOR_EXIT(0x1e),
        OP_CHECK_CAST(0x1f),
        OP_INSTANCE_OF(0x20),
        OP_ARRAY_LENGTH(0x21),
        OP_NEW_INSTANCE(0x22),
        OP_NEW_ARRAY(0x23),
        OP_FILLED_NEW_ARRAY(0x24),
        OP_FILLED_NEW_ARRAY_RANGE(0x25),
        OP_FILL_ARRAY_DATA(0x26),
        OP_THROW(0x27),
        OP_GOTO(0x28),
        OP_GOTO_16(0x29),
        OP_GOTO_32(0x2a),
        OP_PACKED_SWITCH(0x2b),
        OP_SPARSE_SWITCH(0x2c),
        OP_CMPL_FLOAT(0x2d),
        OP_CMPG_FLOAT(0x2e),
        OP_CMPL_DOUBLE(0x2f),
        OP_CMPG_DOUBLE(0x30),
        OP_CMP_LONG(0x31),
        OP_IF_EQ(0x32),
        OP_IF_NE(0x33),
        OP_IF_LT(0x34),
        OP_IF_GE(0x35),
        OP_IF_GT(0x36),
        OP_IF_LE(0x37),
        OP_IF_EQZ(0x38),
        OP_IF_NEZ(0x39),
        OP_IF_LTZ(0x3a),
        OP_IF_GEZ(0x3b),
        OP_IF_GTZ(0x3c),
        OP_IF_LEZ(0x3d),
        OP_UNUSED_3E(0x3e),
        OP_UNUSED_3F(0x3f),
        OP_UNUSED_40(0x40),
        OP_UNUSED_41(0x41),
        OP_UNUSED_42(0x42),
        OP_UNUSED_43(0x43),
        OP_AGET(0x44),
        OP_AGET_WIDE(0x45),
        OP_AGET_OBJECT(0x46),
        OP_AGET_BOOLEAN(0x47),
        OP_AGET_BYTE(0x48),
        OP_AGET_CHAR(0x49),
        OP_AGET_SHORT(0x4a),
        OP_APUT(0x4b),
        OP_APUT_WIDE(0x4c),
        OP_APUT_OBJECT(0x4d),
        OP_APUT_BOOLEAN(0x4e),
        OP_APUT_BYTE(0x4f),
        OP_APUT_CHAR(0x50),
        OP_APUT_SHORT(0x51),
        OP_IGET(0x52),
        OP_IGET_WIDE(0x53),
        OP_IGET_OBJECT(0x54),
        OP_IGET_BOOLEAN(0x55),
        OP_IGET_BYTE(0x56),
        OP_IGET_CHAR(0x57),
        OP_IGET_SHORT(0x58),
        OP_IPUT(0x59),
        OP_IPUT_WIDE(0x5a),
        OP_IPUT_OBJECT(0x5b),
        OP_IPUT_BOOLEAN(0x5c),
        OP_IPUT_BYTE(0x5d),
        OP_IPUT_CHAR(0x5e),
        OP_IPUT_SHORT(0x5f),
        OP_SGET(0x60),
        OP_SGET_WIDE(0x61),
        OP_SGET_OBJECT(0x62),
        OP_SGET_BOOLEAN(0x63),
        OP_SGET_BYTE(0x64),
        OP_SGET_CHAR(0x65),
        OP_SGET_SHORT(0x66),
        OP_SPUT(0x67),
        OP_SPUT_WIDE(0x68),
        OP_SPUT_OBJECT(0x69),
        OP_SPUT_BOOLEAN(0x6a),
        OP_SPUT_BYTE(0x6b),
        OP_SPUT_CHAR(0x6c),
        OP_SPUT_SHORT(0x6d),
        OP_INVOKE_VIRTUAL(0x6e),
        OP_INVOKE_SUPER(0x6f),
        OP_INVOKE_DIRECT(0x70),
        OP_INVOKE_STATIC(0x71),
        OP_INVOKE_INTERFACE(0x72),
        OP_UNUSED_73(0x73),
        OP_INVOKE_VIRTUAL_RANGE(0x74),
        OP_INVOKE_SUPER_RANGE(0x75),
        OP_INVOKE_DIRECT_RANGE(0x76),
        OP_INVOKE_STATIC_RANGE(0x77),
        OP_INVOKE_INTERFACE_RANGE(0x78),
        OP_UNUSED_79(0x79),
        OP_UNUSED_7A(0x7a),
        OP_NEG_INT(0x7b),
        OP_NOT_INT(0x7c),
        OP_NEG_LONG(0x7d),
        OP_NOT_LONG(0x7e),
        OP_NEG_FLOAT(0x7f),
        OP_NEG_DOUBLE(0x80),
        OP_INT_TO_LONG(0x81),
        OP_INT_TO_FLOAT(0x82),
        OP_INT_TO_DOUBLE(0x83),
        OP_LONG_TO_INT(0x84),
        OP_LONG_TO_FLOAT(0x85),
        OP_LONG_TO_DOUBLE(0x86),
        OP_FLOAT_TO_INT(0x87),
        OP_FLOAT_TO_LONG(0x88),
        OP_FLOAT_TO_DOUBLE(0x89),
        OP_DOUBLE_TO_INT(0x8a),
        OP_DOUBLE_TO_LONG(0x8b),
        OP_DOUBLE_TO_FLOAT(0x8c),
        OP_INT_TO_BYTE(0x8d),
        OP_INT_TO_CHAR(0x8e),
        OP_INT_TO_SHORT(0x8f),
        OP_ADD_INT(0x90),
        OP_SUB_INT(0x91),
        OP_MUL_INT(0x92),
        OP_DIV_INT(0x93),
        OP_REM_INT(0x94),
        OP_AND_INT(0x95),
        OP_OR_INT(0x96),
        OP_XOR_INT(0x97),
        OP_SHL_INT(0x98),
        OP_SHR_INT(0x99),
        OP_USHR_INT(0x9a),
        OP_ADD_LONG(0x9b),
        OP_SUB_LONG(0x9c),
        OP_MUL_LONG(0x9d),
        OP_DIV_LONG(0x9e),
        OP_REM_LONG(0x9f),
        OP_AND_LONG(0xa0),
        OP_OR_LONG(0xa1),
        OP_XOR_LONG(0xa2),
        OP_SHL_LONG(0xa3),
        OP_SHR_LONG(0xa4),
        OP_USHR_LONG(0xa5),
        OP_ADD_FLOAT(0xa6),
        OP_SUB_FLOAT(0xa7),
        OP_MUL_FLOAT(0xa8),
        OP_DIV_FLOAT(0xa9),
        OP_REM_FLOAT(0xaa),
        OP_ADD_DOUBLE(0xab),
        OP_SUB_DOUBLE(0xac),
        OP_MUL_DOUBLE(0xad),
        OP_DIV_DOUBLE(0xae),
        OP_REM_DOUBLE(0xaf),
        OP_ADD_INT_2ADDR(0xb0),
        OP_SUB_INT_2ADDR(0xb1),
        OP_MUL_INT_2ADDR(0xb2),
        OP_DIV_INT_2ADDR(0xb3),
        OP_REM_INT_2ADDR(0xb4),
        OP_AND_INT_2ADDR(0xb5),
        OP_OR_INT_2ADDR(0xb6),
        OP_XOR_INT_2ADDR(0xb7),
        OP_SHL_INT_2ADDR(0xb8),
        OP_SHR_INT_2ADDR(0xb9),
        OP_USHR_INT_2ADDR(0xba),
        OP_ADD_LONG_2ADDR(0xbb),
        OP_SUB_LONG_2ADDR(0xbc),
        OP_MUL_LONG_2ADDR(0xbd),
        OP_DIV_LONG_2ADDR(0xbe),
        OP_REM_LONG_2ADDR(0xbf),
        OP_AND_LONG_2ADDR(0xc0),
        OP_OR_LONG_2ADDR(0xc1),
        OP_XOR_LONG_2ADDR(0xc2),
        OP_SHL_LONG_2ADDR(0xc3),
        OP_SHR_LONG_2ADDR(0xc4),
        OP_USHR_LONG_2ADDR(0xc5),
        OP_ADD_FLOAT_2ADDR(0xc6),
        OP_SUB_FLOAT_2ADDR(0xc7),
        OP_MUL_FLOAT_2ADDR(0xc8),
        OP_DIV_FLOAT_2ADDR(0xc9),
        OP_REM_FLOAT_2ADDR(0xca),
        OP_ADD_DOUBLE_2ADDR(0xcb),
        OP_SUB_DOUBLE_2ADDR(0xcc),
        OP_MUL_DOUBLE_2ADDR(0xcd),
        OP_DIV_DOUBLE_2ADDR(0xce),
        OP_REM_DOUBLE_2ADDR(0xcf),
        OP_ADD_INT_LIT16(0xd0),
        OP_RSUB_INT(0xd1),
        OP_MUL_INT_LIT16(0xd2),
        OP_DIV_INT_LIT16(0xd3),
        OP_REM_INT_LIT16(0xd4),
        OP_AND_INT_LIT16(0xd5),
        OP_OR_INT_LIT16(0xd6),
        OP_XOR_INT_LIT16(0xd7),
        OP_ADD_INT_LIT8(0xd8),
        OP_RSUB_INT_LIT8(0xd9),
        OP_MUL_INT_LIT8(0xda),
        OP_DIV_INT_LIT8(0xdb),
        OP_REM_INT_LIT8(0xdc),
        OP_AND_INT_LIT8(0xdd),
        OP_OR_INT_LIT8(0xde),
        OP_XOR_INT_LIT8(0xdf),
        OP_SHL_INT_LIT8(0xe0),
        OP_SHR_INT_LIT8(0xe1),
        OP_USHR_INT_LIT8(0xe2),
        OP_IGET_VOLATILE(0xe3),
        OP_IPUT_VOLATILE(0xe4),
        OP_SGET_VOLATILE(0xe5),
        OP_SPUT_VOLATILE(0xe6),
        OP_IGET_OBJECT_VOLATILE(0xe7),
        OP_IGET_WIDE_VOLATILE(0xe8),
        OP_IPUT_WIDE_VOLATILE(0xe9),
        OP_SGET_WIDE_VOLATILE(0xea),
        OP_SPUT_WIDE_VOLATILE(0xeb),
        OP_BREAKPOINT(0xec),
        OP_THROW_VERIFICATION_ERROR(0xed),
        OP_EXECUTE_INLINE(0xee),
        OP_EXECUTE_INLINE_RANGE(0xef),
        OP_INVOKE_OBJECT_INIT_RANGE(0xf0),
        OP_RETURN_VOID_BARRIER(0xf1),
        OP_IGET_QUICK(0xf2),
        OP_IGET_WIDE_QUICK(0xf3),
        OP_IGET_OBJECT_QUICK(0xf4),
        OP_IPUT_QUICK(0xf5),
        OP_IPUT_WIDE_QUICK(0xf6),
        OP_IPUT_OBJECT_QUICK(0xf7),
        OP_INVOKE_VIRTUAL_QUICK(0xf8),
        OP_INVOKE_VIRTUAL_QUICK_RANGE(0xf9),
        OP_INVOKE_SUPER_QUICK(0xfa),
        OP_INVOKE_SUPER_QUICK_RANGE(0xfb),
        OP_IPUT_OBJECT_VOLATILE(0xfc),
        OP_SGET_OBJECT_VOLATILE(0xfd),
        OP_SPUT_OBJECT_VOLATILE(0xfe),
        OP_UNUSED_FF(0xff);
        // END(libdex-opcode-enum)


        // 定义私有变量
        private int nCode;

        // 构造函数，枚举类型只能为私有
        private Opcode(int _nCode) {
            this.nCode = _nCode;
        }

        @Override
        public String toString() {
            return String.valueOf(this.nCode);
        }
    }

    ;


    public static String gOpNames[] = {
            // BEGIN(libdex-opcode-names); GENERATED AUTOMATICALLY BY opcode-gen
            "nop",
            "move",
            "move/from16",
            "move/16",
            "move-wide",
            "move-wide/from16",
            "move-wide/16",
            "move-object",
            "move-object/from16",
            "move-object/16",
            "move-result",
            "move-result-wide",
            "move-result-object",
            "move-exception",
            "return-void",
            "return",
            "return-wide",
            "return-object",
            "const/4",
            "const/16",
            "const",
            "const/high16",
            "const-wide/16",
            "const-wide/32",
            "const-wide",
            "const-wide/high16",
            "const-string",
            "const-string/jumbo",
            "const-class",
            "monitor-enter",
            "monitor-exit",
            "check-cast",
            "instance-of",
            "array-length",
            "new-instance",
            "new-array",
            "filled-new-array",
            "filled-new-array/range",
            "fill-array-data",
            "throw",
            "goto",
            "goto/16",
            "goto/32",
            "packed-switch",
            "sparse-switch",
            "cmpl-float",
            "cmpg-float",
            "cmpl-double",
            "cmpg-double",
            "cmp-long",
            "if-eq",
            "if-ne",
            "if-lt",
            "if-ge",
            "if-gt",
            "if-le",
            "if-eqz",
            "if-nez",
            "if-ltz",
            "if-gez",
            "if-gtz",
            "if-lez",
            "unused-3e",
            "unused-3f",
            "unused-40",
            "unused-41",
            "unused-42",
            "unused-43",
            "aget",
            "aget-wide",
            "aget-object",
            "aget-boolean",
            "aget-byte",
            "aget-char",
            "aget-short",
            "aput",
            "aput-wide",
            "aput-object",
            "aput-boolean",
            "aput-byte",
            "aput-char",
            "aput-short",
            "iget",
            "iget-wide",
            "iget-object",
            "iget-boolean",
            "iget-byte",
            "iget-char",
            "iget-short",
            "iput",
            "iput-wide",
            "iput-object",
            "iput-boolean",
            "iput-byte",
            "iput-char",
            "iput-short",
            "sget",
            "sget-wide",
            "sget-object",
            "sget-boolean",
            "sget-byte",
            "sget-char",
            "sget-short",
            "sput",
            "sput-wide",
            "sput-object",
            "sput-boolean",
            "sput-byte",
            "sput-char",
            "sput-short",
            "invoke-virtual",
            "invoke-super",
            "invoke-direct",
            "invoke-static",
            "invoke-interface",
            "unused-73",
            "invoke-virtual/range",
            "invoke-super/range",
            "invoke-direct/range",
            "invoke-static/range",
            "invoke-interface/range",
            "unused-79",
            "unused-7a",
            "neg-int",
            "not-int",
            "neg-long",
            "not-long",
            "neg-float",
            "neg-double",
            "int-to-long",
            "int-to-float",
            "int-to-double",
            "long-to-int",
            "long-to-float",
            "long-to-double",
            "float-to-int",
            "float-to-long",
            "float-to-double",
            "double-to-int",
            "double-to-long",
            "double-to-float",
            "int-to-byte",
            "int-to-char",
            "int-to-short",
            "add-int",
            "sub-int",
            "mul-int",
            "div-int",
            "rem-int",
            "and-int",
            "or-int",
            "xor-int",
            "shl-int",
            "shr-int",
            "ushr-int",
            "add-long",
            "sub-long",
            "mul-long",
            "div-long",
            "rem-long",
            "and-long",
            "or-long",
            "xor-long",
            "shl-long",
            "shr-long",
            "ushr-long",
            "add-float",
            "sub-float",
            "mul-float",
            "div-float",
            "rem-float",
            "add-double",
            "sub-double",
            "mul-double",
            "div-double",
            "rem-double",
            "add-int/2addr",
            "sub-int/2addr",
            "mul-int/2addr",
            "div-int/2addr",
            "rem-int/2addr",
            "and-int/2addr",
            "or-int/2addr",
            "xor-int/2addr",
            "shl-int/2addr",
            "shr-int/2addr",
            "ushr-int/2addr",
            "add-long/2addr",
            "sub-long/2addr",
            "mul-long/2addr",
            "div-long/2addr",
            "rem-long/2addr",
            "and-long/2addr",
            "or-long/2addr",
            "xor-long/2addr",
            "shl-long/2addr",
            "shr-long/2addr",
            "ushr-long/2addr",
            "add-float/2addr",
            "sub-float/2addr",
            "mul-float/2addr",
            "div-float/2addr",
            "rem-float/2addr",
            "add-double/2addr",
            "sub-double/2addr",
            "mul-double/2addr",
            "div-double/2addr",
            "rem-double/2addr",
            "add-int/lit16",
            "rsub-int",
            "mul-int/lit16",
            "div-int/lit16",
            "rem-int/lit16",
            "and-int/lit16",
            "or-int/lit16",
            "xor-int/lit16",
            "add-int/lit8",
            "rsub-int/lit8",
            "mul-int/lit8",
            "div-int/lit8",
            "rem-int/lit8",
            "and-int/lit8",
            "or-int/lit8",
            "xor-int/lit8",
            "shl-int/lit8",
            "shr-int/lit8",
            "ushr-int/lit8",
            "+iget-volatile",
            "+iput-volatile",
            "+sget-volatile",
            "+sput-volatile",
            "+iget-object-volatile",
            "+iget-wide-volatile",
            "+iput-wide-volatile",
            "+sget-wide-volatile",
            "+sput-wide-volatile",
            "^breakpoint",
            "^throw-verification-error",
            "+execute-inline",
            "+execute-inline/range",
            "+invoke-object-init/range",
            "+return-void-barrier",
            "+iget-quick",
            "+iget-wide-quick",
            "+iget-object-quick",
            "+iput-quick",
            "+iput-wide-quick",
            "+iput-object-quick",
            "+invoke-virtual-quick",
            "+invoke-virtual-quick/range",
            "+invoke-super-quick",
            "+invoke-super-quick/range",
            "+iput-object-volatile",
            "+sget-object-volatile",
            "+sput-object-volatile",
            "unused-ff"
            // END(libdex-opcode-names)
    };
    public static String Format[] = {
            "10x",
            "12x",
            "22x",
            "32x",
            "12x",
            "22x",
            "32x",
            "12x",
            "22x",
            "32x",
            "11x",
            "11x",
            "11x",
            "11x",
            "10x",
            "11x",
            "11x",
            "11x",
            "11n",
            "21s",
            "31i",
            "21h",
            "21s",
            "31i",
            "51l",
            "21h",
            "21c",
            "31c",
            "21c",
            "11x",
            "11x",
            "21c",
            "22c",
            "12x",
            "21c",
            "22c",
            "35c",
            "3rc",
            "31t",
            "11x",
            "10t",
            "20t",
            "30t",
            "31t",
            "31t",


            "23x",
            "23x",
            "23x",
            "23x",
            "23x",


            "22t",
            "22t",
            "22t",
            "22t",
            "22t",
            "22t",

            "21t",
            "21t",
            "21t",
            "21t",
            "21t",
            "21t",

            "10x",
            "10x",
            "10x",
            "10x",
            "10x",
            "10x",


            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",


            "22c",
            "22c",
            "22c",
            "22c",
            "22c",
            "22c",
            "22c",
            "22c",
            "22c",
            "22c",
            "22c",
            "22c",
            "22c",
            "22c",


            "21c",
            "21c",
            "21c",
            "21c",
            "21c",
            "21c",
            "21c",
            "21c",
            "21c",
            "21c",
            "21c",
            "21c",
            "21c",
            "21c",


            "35c",
            "35c",
            "35c",
            "35c",
            "35c",


            "10x",


            "3rc",
            "3rc",
            "3rc",
            "3rc",
            "3rc",


            "10x",
            "10x",


            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",


            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",
            "23x",


            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",
            "12x",


            "22s",
            "22s",
            "22s",
            "22s",
            "22s",
            "22s",
            "22s",
            "22s",

            "22b",
            "22b",
            "22b",
            "22b",
            "22b",
            "22b",
            "22b",
            "22b",
            "22b",
            "22b",
            "22b",


            "10x",
            "10x",
            "10x",
            "10x",
            "10x",
            "10x",
            "10x",
            "10x",
            "10x",
            "10x",
            "10x",
            "10x",
            "10x",
            "10x",
            "10x",
            "10x",
            "10x",
            "10x",
            "10x",
            "10x",
            "10x",
            "10x",
            "10x",
            "10x",
            "10x",
            "10x",
            "10x",
            "10x",
            "10x",


    };


    public static String getOpcode(){

        if (insns_index1>=opcodess.size()){
            System.out.println("cuole");
        }


        String vB = opcodess.get(insns_index1);
        insns_index1++;
        return vB;
    }
    public static int getOpcode(Header_Items.Header_Class.Code_item code_item){



        byte[] bytes = code_item.insns_byte.get(insns_index1);
        insns_index1++;



        return PaserUtil.byte2int(bytes);
    }


    public static int insns_index1;
    public static List<String> opcodess;
    public static Map<String, Object> getforFormat(String gOpFormat, String gOpName, Header_Items.Header_Class.Code_item code_item, int insns_index) {
       // List<String> opcodes;
        Map<String, Object> objectMap = new HashMap<>();
        String s;
        String substring1;
        String substring2;
        String vB = "";
        int vBb;
        String vA;
        String vC;
        String vD;
        String vE;
        String vF;
        String vG;
        String vH;
        String s1;
        insns_index1=insns_index;
        opcodess=code_item.insns;
        String haha = "";
        switch (gOpFormat) {
            case "10x"://op    ;   ØØ|op
                insns_index1++;
                break;
            case "12x"://op vA, vB     ;;B|A|op


                s = getOpcode();
                vB = "v" + s.substring(2, 3);;
                vA = "v" + s.substring(3,4);;

                haha = vA + ", " + vB;
                break;
            case "11n"://op vA, #+B      ;


                s = getOpcode();

                vB = "v" + s.substring(2, 3);;
                vA = s.substring(3,4);;
                haha = vA + ", " + vB;
                
                break;
            case "11x"://op vAA    ;   AA|op

                s = getOpcode();

                vA = "v" + s.substring(2, 4);;
                haha = vA ;
                
                break;
            case "10t"://op +AA


                s = getOpcode();

                haha = s.substring(2, 4);;
                
                break;
            case "20t"://op +AAAA     ;    ØØ|op AAAA
                
                haha = getOpcode();

                break;
            case "22x"://op vAA, vBBBB     ;   AA|op BBBB

                s = getOpcode();
                vA = "v" + s.substring(2, 4);;

                

                vB = "v" +getOpcode();


                haha = vA + ", " + vB;

                break;
            case "21t"://op vAA, +BBBB

                s = getOpcode();
                vA = "v" + s.substring(2, 4);;

                

                vB = getOpcode();


                haha = vA + ", " + vB;

                break;
            case "21s"://op vAA, #+BBBB


                s = getOpcode();
                vA = "v" + s.substring(2, 4);;

                

                vB = getOpcode();


                haha = vA + ", " + vB;


                break;
            case "21h"://AA|op BBBB
                //op vAA, #+BBBB0000;;;;//op vAA, #+BBBB000000000000


                s = getOpcode();
                vA = "v" + s.substring(2, 4);;

                

                vB = "v" +getOpcode();


                haha = vA + ", " + vB;


                break;
            case "21c"://AA|op BBBB
                //op vAA, type@BBBB    ;  op vAA, field@BBBB ; op vAA, string@BBBB
                s = getOpcode();
                vA = "v" + s.substring(2, 4);;

                if (gOpName.contains("sput")||gOpName.contains("sget")){
                    vB=PaserItemsPaser.header_field.get(getOpcode(code_item)).toString();
                }else if (gOpName.contains("check-cast")||gOpName.contains("new-instance")||gOpName.contains("const-class")){
                    vB=PaserItemsPaser.header_types.get(getOpcode(code_item)).toString();
                }else  if (gOpName.contains("const-string")){
                    vB=PaserItemsPaser.header_strings.get(getOpcode(code_item)).str;
                }



                haha = vA + ", " + vB;


                break;
            case "23x"://	op vAA, vBB, vCC   ;AA|op CC|BB

                s = getOpcode();
                vA = "v" + s.substring(2, 4);;
                s1 = getOpcode();
                vB = "v" + s1.substring(2, 4);;
                vC = "v" + s1.substring(0, 2);;

                haha=vA+", "+vB+", "+vC;

                break;
            case "22b"://op vAA, vBB, #+CC      ;AA|op CC|BB

                s = getOpcode();
                vA = "v" + s.substring(2, 4);;

                
                 s1 = getOpcode();
                vB =  s1.substring(2, 4);;
                vC = "v" + s1.substring(0, 2);;

                haha=vA+", "+vB+", "+vC;


                break;
            case "22t"://op vA, vB, +CCCC     :    B|A|op CCCC
                s = getOpcode();
                vB = "v" + s.substring(0, 1);;
                vA = "v" + s.substring(1, 2);;
                
                vC = getOpcode();

                haha=vA+", "+vB+", "+vC;

                break;
            case "22s"://	op vA, vB, #+CCCC

                s = getOpcode();
                vB = "v" + s.substring(0, 1);;
                vA = "v" + s.substring(1, 2);;
                
                vC = getOpcode();

                haha=vA+", "+vB+", "+vC;

                break;
            case "22c"://op vA, vB, type@CCCC   ;;;  op vA, vB, field@CCCC

                s = getOpcode();
                vB = "v" + s.substring(0, 1);;
                vA = "v" + s.substring(1, 2);;
                
                vC = getOpcode();

                haha=vA+", "+vB+", "+vC;

                break;
            case "22cs"://op vA, vB, fieldoff@CCCC

                s = getOpcode();
                vB = "v" + s.substring(0, 1);;
                vA = "v" + s.substring(1, 2);;
                
                vC = getOpcode();

                haha=vA+", "+vB+", "+vC;

                break;
            case "30t"://op +AAAAAAAA    ;  ØØ|op AAAAlo AAAAhi
                
                s = getOpcode();
                
                s1 = getOpcode();
                haha=s+s1;

                break;
            case "32x"://	op vAAAA, vBBBB    ;ØØ|op AAAA BBBB

                
                s = getOpcode();
                
                s1 = getOpcode();
                haha=s+", "+s1;

                break;
            case "31i"://op vAA, #+BBBBBBBB     ;  AA|op BBBBlo BBBBhi
                s = getOpcode();
                vA = "v" + s.substring(2, 4);;

                
                s = getOpcode();
                
                s1 = getOpcode();
                haha=vA+", "+s+s1;

                break;

            case "31t"://	op vAA, +BBBBBBBB

                s = getOpcode();
                vA = "v" + s.substring(2, 4);;

                
                s = getOpcode();
                
                s1 = getOpcode();
                haha=vA+", "+s+s1;

                break;
            case "31c"://	op vAA, string@BBBBBBBB

                s = getOpcode();
                vA = "v" + s.substring(2, 4);;

                
                s = getOpcode();
                
                s1 = getOpcode();
                haha=vA+", "+s+s1;

                break;
            case "35c":  //B|A|op CCCC G|F|E|D

/*               [B=5] op {vD, vE, vF, vG, vA}, meth@CCCC
[B=5] op {vD, vE, vF, vG, vA}, type@CCCC
[B=4] op {vD, vE, vF, vG}, kind@CCCC
[B=3] op {vD, vE, vF}, kind@CCCC
[B=2] op {vD, vE}, kind@CCCC
[B=1] op {vD}, kind@CCCC
[B=0] op {}, kind@CCCC*/

                s = getOpcode();
                vA = "v" + s.substring(3, 4);;
                vBb = Integer.parseInt(s.substring(2, 3),16);;

                
                int vCx = getOpcode(code_item);

                
                s = getOpcode();
                vG = "v" + s.substring(0, 1);;
                vF = "v" + s.substring(1, 2);;
                vE = "v" + s.substring(2, 3);;
                vD = "v" + s.substring(3, 4);;


                switch (vBb){
                    case 5:
                        haha="{"+vD+","+vE+","+vF+","+vG+","+vA+"},"+PaserItemsPaser.header_method.get(vCx).toString();
                        break;
                    case 4:
                        haha="{"+vD+","+vE+","+vF+","+vG+"},"+PaserItemsPaser.header_method.get(vCx).toString();
                        break;
                    case 3:
                        haha="{"+vD+","+vE+","+vF+"},"+PaserItemsPaser.header_method.get(vCx).toString();
                        break;
                    case 2:
                        haha="{"+vD+","+vE+"},"+PaserItemsPaser.header_method.get(vCx).toString();
                        break;
                    case 1:
                        haha="{"+vD+"},"+PaserItemsPaser.header_method.get(vCx).toString();
                        break;
                    case 0:
                        haha="{},"+PaserItemsPaser.header_method.get(vCx).toString();;
                        break;
                }


                break;
            case "35ms":  //B|A|op CCCC G|F|E|D

/*               	[B=5] op {vD, vE, vF, vG, vA}, vtaboff@CCCC
[B=4] op {vD, vE, vF, vG}, vtaboff@CCCC
[B=3] op {vD, vE, vF}, vtaboff@CCCC
[B=2] op {vD, vE}, vtaboff@CCCC
[B=1] op {vD}, vtaboff@CCCC*/
                s = getOpcode();
                vA = "v" + s.substring(3, 4);;
                 vBb = Integer.parseInt(s.substring(2, 3));;

                
                vC = getOpcode();

                
                s = getOpcode();
                vG = "v" + s.substring(0, 1);;
                vF = "v" + s.substring(1, 2);;
                vE = "v" + s.substring(2, 3);;
                vD = "v" + s.substring(3, 4);;


                switch (vBb){
                    case 5:
                        haha="{"+vD+","+vE+","+vF+","+vG+","+vA+"},"+vC;
                        break;
                    case 4:
                        haha="{"+vD+","+vE+","+vF+","+vG+"},"+vC;
                        break;
                    case 3:
                        haha="{"+vD+","+vE+","+vF+"},"+vC;
                        break;
                    case 2:
                        haha="{"+vD+","+vE+"},"+vC;
                        break;
                    case 1:
                        haha="{"+vD+"},"+vC;
                        break;

                }
                break;
            case "35fs":   //B|A|op DDCC H|G|F|E

/*[B=5] op {vE, vF, vG, vH, vA}, vtaboff@CC, iface@DD
[B=4] op {vE, vF, vG, vH}, vtaboff@CC, iface@DD
[B=3] op {vE, vF, vG}, vtaboff@CC, iface@DD
[B=2] op {vE, vF}, vtaboff@CC, iface@DD
[B=1] op {vE}, vtaboff@CC, iface@DD*/
                s = getOpcode();
                vA = "v" + s.substring(3, 4);;
                 vBb = Integer.parseInt(s.substring(2, 3));;

                
                s = getOpcode();

                vD=s.substring(0, 2);
                vC=s.substring(2, 4);

                
                s = getOpcode();
                vH = "v" + s.substring(0, 1);;
                vG = "v" + s.substring(1, 2);;
                vF = "v" + s.substring(2, 3);;
                vE = "v" + s.substring(3, 4);;


                switch (vBb){
                    case 5:
                        haha="{"+vE+","+vF+","+vG+","+vH+","+vA+"},"+vC+", "+vD;
                        break;
                    case 4:
                        haha="{"+vE+","+vF+","+vG+","+vH+"},"+vC+", "+vD;
                        break;
                    case 3:
                        haha="{"+vE+","+vF+","+vG+"},"+vC+", "+vD;
                        break;
                    case 2:
                        haha="{"+vE+","+vF+"},"+vC+", "+vD;
                        break;
                    case 1:
                        haha="{"+vE+"},"+vC+", "+vD;
                        break;

                }
                break;

            case "3rc"://AA|op BBBB CCCC
                //op {vCCCC .. vNNNN}, meth@BBBB  ; op {vCCCC .. vNNNN}, type@BBBB
               // (where NNNN = CCCC+AA-1, that is A determines the count 0..255, and C determines the first register)
                s = getOpcode();
                String substring = s.substring(2, 4);
                int vAa= Integer.parseInt(substring,16);
                
                int vBx = getOpcode(code_item);

                
                int vCxx = getOpcode(code_item);


                haha="{v"+vCxx+"..v"+(vCxx+vAa-1)+"},"+vBx;


                break;
            case "3rms"://op {vCCCC .. vNNNN}, vtaboff@BBBB

                s = getOpcode();

                int vAaa= Integer.parseInt(s.substring(2, 4));

                int vBxa = getOpcode(code_item);


                int vCxa = getOpcode(code_item);


                haha="{v"+vCxa+"..v"+(vCxa+vAaa-1)+"},"+vBxa;


                break;
            case "3rfs"://AA|op CCBB DDDD
                //op {vDDDD .. vNNNN}, vtaboff@BB, iface@CC
                //(where NNNN = DDDD+AA-1, that is A determines the count 0..255, and D determines the first register)

                s = getOpcode();
                vA=s.substring(3,4);
                int vAaaa= Integer.parseInt(vA);
                
                s = getOpcode();
                vB=s.substring(0,2);
                vC=s.substring(2,4);


                int vDx = getOpcode(code_item);

                haha="{v"+vDx+" .. v"+(vDx+vAaaa-1)+"},"+vB+","+vC;

                break;
            case "51l"://AA|op BBBBlo BBBB BBBB BBBBhi
                //	op vAA, #+BBBBBBBBBBBBBBBB

                s = getOpcode();
                vA=s.substring(2,4);


                vBb = getOpcode(code_item);


                vBb+= getOpcode(code_item);


                vBb+= getOpcode(code_item);


                vBb+= getOpcode(code_item);

                haha=vA+","+vB;
                break;

        }
        objectMap.put("size", insns_index1);
        objectMap.put("code", gOpName + " " + haha);
        return objectMap;
    }


    public static void gethaha(String gOpFormat) {
        int codeOff = gOpFormat.charAt(0) - '0';
        int regist = gOpFormat.charAt(1) - '0';
        char c3 = gOpFormat.charAt(2);
    }

}
