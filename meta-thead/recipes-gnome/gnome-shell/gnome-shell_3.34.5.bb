SUMMARY = "GNOME Shell is the graphical shell of the GNOME desktop environment"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

GNOMEBASEBUILDCLASS = "meson"

inherit gnomebase gsettings gettext gobject-introspection features_check upstream-version-is-even bash-completion

REQUIRED_DISTRO_FEATURES = "x11 polkit systemd pam"

DEPENDS = " \
    libxml2-native \
    sassc-native \
    gtk+3 \
    mutter \
    evolution-data-server \
    gcr \
    gjs \
    gnome-autoar \
    polkit \
    libcroco \
    startup-notification \
    ibus \
    gsettings-desktop-schemas \
"

GTKDOC_MESON_OPTION = "gtk_doc"

# gobject-introspection is mandatory and cannot be configured
REQUIRED_DISTRO_FEATURES += "gobject-introspection-data"
UNKNOWN_CONFIGURE_OPT_IGNORE:append = " introspection"
GIR_MESON_OPTION = ""

SRC_URI += "file://0001-Fix-configure-error.patch"

SRC_URI[archive.md5sum] = "4bd27c8a91d30fde78cb69b94677cf1f"
SRC_URI[archive.sha256sum] = "d296f318a74a6d7883358a6ce1c4d8808b7903dbbb4f9c61ab4230f18e6d7550"

PACKAGECONFIG ??= "bluetooth nm ${@bb.utils.filter('DISTRO_FEATURES', 'systemd', d)}"
PACKAGECONFIG[bluetooth] = ",,gnome-bluetooth3"
PACKAGECONFIG[nm] = "-Dnetworkmanager=true, -Dnetworkmanager=false, networkmanager"
PACKAGECONFIG[systemd] = "-Dsystemd=true, -Dsystemd=false, systemd"

EXTRA_OEMESON = " \
    -Dman=false \
"

do_install:append() {
    # fix shebangs
    for tool in `find ${D}${bindir} -name '*-tool'`; do
        sed -i 's:#!${PYTHON}:#!${bindir}/${PYTHON_PN}:' $tool
    done
}

FILES:${PN} += " \
    ${datadir}/dbus-1 \
    ${datadir}/gnome-control-center \
    ${datadir}/xdg-desktop-portal \
    ${systemd_user_unitdir} \
"

RDEPENDS:${PN} += "gsettings-desktop-schemas gdm-base librsvg-gtk"

PACKAGES =+ "${PN}-tools"
FILES:${PN}-tools = "${bindir}/*-tool"
RDEPENDS:${PN}-tools = "python3-core"

