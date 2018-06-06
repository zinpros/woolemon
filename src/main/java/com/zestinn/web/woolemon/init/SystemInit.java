package com.zestinn.web.woolemon.init;

public class SystemInit {

    private SystemInit(){

    }

    public static class Holder{
        private static SystemInit si = new SystemInit();
    }

    public static SystemInit getInstance(){
                return Holder.si;
    }


}
