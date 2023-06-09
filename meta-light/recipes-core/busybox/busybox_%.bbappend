# Example use of configuration fragments for busybox, which uses the same
# mechanism as the linux-yocto kernel recipe.
#
# The entries here will override any entries in the base busybox recipe
#
# More details can be found in the Kernel Dev Manual
# http://www.yoctoproject.org/docs/current/kernel-dev/kernel-dev.html#changing-the-configuration
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
	    file://netcat_server_opt.cfg \
	    file://mkfs.cfg \
	    file://resize.cfg \
	    file://top.cfg \
	    file://wchar.cfg \
"
