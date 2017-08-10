// IImageManager.aidl
package com.morladim.morganrss;

// Declare any non-default types here with import statements

interface IImageManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void aa(inout byte[] bitmap);

    void a(int c);

    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}
