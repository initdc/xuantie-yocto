
do_configure:prepend() {
   cp -rf ${QEMU_TOOLCHAIN_PATH}/*  ${WORKDIR}/recipe-sysroot-native/usr/bin/ 
}
