package com.qiangdong.reader.utils;

/**
 * 用户角色位运算工具类
 */
public class BitOperationUtil {

    public static int removeBitOperationPower(int userAllRole, int roleId) {
        if (checkBitOperationPower(userAllRole, roleId)) {
            return userAllRole - ((int) Math.pow(2, roleId));
        }
        return userAllRole;
    }

    public static int addBitOperationPower(int userAllRole, int roleId) {
        if (checkBitOperationPower(userAllRole, roleId)) {
            return userAllRole;
        }
        return userAllRole += (int) Math.pow(2, roleId);
    }

    public static boolean checkBitOperationPower(int userAllRole, int checkRoleId) {
        int purviewValue = (int) Math.pow(2, checkRoleId);
        return (userAllRole & purviewValue) == purviewValue;
    }
}
