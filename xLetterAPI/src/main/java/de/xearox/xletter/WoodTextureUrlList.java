package de.xearox.xletter;

public enum WoodTextureUrlList {
	A("http://textures.minecraft.net/texture/a67d813ae7ffe5be951a4f41f2aa619a5e3894e85ea5d4986f84949c63d7672e"),
	B("http://textures.minecraft.net/texture/50c1b584f13987b466139285b2f3f28df6787123d0b32283d8794e3374e23"),
	C("http://textures.minecraft.net/texture/abe983ec478024ec6fd046fcdfa4842676939551b47350447c77c13af18e6f"),
	D("http://textures.minecraft.net/texture/3193dc0d4c5e80ff9a8a05d2fcfe269539cb3927190bac19da2fce61d71"),
	E("http://textures.minecraft.net/texture/dbb2737ecbf910efe3b267db7d4b327f360abc732c77bd0e4eff1d510cdef"),
	F("http://textures.minecraft.net/texture/b183bab50a3224024886f25251d24b6db93d73c2432559ff49e459b4cd6a"),
	G("http://textures.minecraft.net/texture/1ca3f324beeefb6a0e2c5b3c46abc91ca91c14eba419fa4768ac3023dbb4b2"),
	H("http://textures.minecraft.net/texture/31f3462a473549f1469f897f84a8d4119bc71d4a5d852e85c26b588a5c0c72f"),
	I("http://textures.minecraft.net/texture/46178ad51fd52b19d0a3888710bd92068e933252aac6b13c76e7e6ea5d3226"),
	J("http://textures.minecraft.net/texture/3a79db9923867e69c1dbf17151e6f4ad92ce681bcedd3977eebbc44c206f49"),
	K("http://textures.minecraft.net/texture/9461b38c8e45782ada59d16132a4222c193778e7d70c4542c9536376f37be42"),
	L("http://textures.minecraft.net/texture/319f50b432d868ae358e16f62ec26f35437aeb9492bce1356c9aa6bb19a386"),
	M("http://textures.minecraft.net/texture/49c45a24aaabf49e217c15483204848a73582aba7fae10ee2c57bdb76482f"),
	N("http://textures.minecraft.net/texture/35b8b3d8c77dfb8fbd2495c842eac94fffa6f593bf15a2574d854dff3928"),
	O("http://textures.minecraft.net/texture/d11de1cadb2ade61149e5ded1bd885edf0df6259255b33b587a96f983b2a1"),
	P("http://textures.minecraft.net/texture/a0a7989b5d6e621a121eedae6f476d35193c97c1a7cb8ecd43622a485dc2e912"),
	Q("http://textures.minecraft.net/texture/43609f1faf81ed49c5894ac14c94ba52989fda4e1d2a52fd945a55ed719ed4"),
	R("http://textures.minecraft.net/texture/a5ced9931ace23afc351371379bf05c635ad186943bc136474e4e5156c4c37"),
	S("http://textures.minecraft.net/texture/3e41c60572c533e93ca421228929e54d6c856529459249c25c32ba33a1b1517"),
	T("http://textures.minecraft.net/texture/1562e8c1d66b21e459be9a24e5c027a34d269bdce4fbee2f7678d2d3ee4718"),
	U("http://textures.minecraft.net/texture/607fbc339ff241ac3d6619bcb68253dfc3c98782baf3f1f4efdb954f9c26"),
	V("http://textures.minecraft.net/texture/cc9a138638fedb534d79928876baba261c7a64ba79c424dcbafcc9bac7010b8"),
	W("http://textures.minecraft.net/texture/269ad1a88ed2b074e1303a129f94e4b710cf3e5b4d995163567f68719c3d9792"),
	X("http://textures.minecraft.net/texture/5a6787ba32564e7c2f3a0ce64498ecbb23b89845e5a66b5cec7736f729ed37"),
	Y("http://textures.minecraft.net/texture/c52fb388e33212a2478b5e15a96f27aca6c62ac719e1e5f87a1cf0de7b15e918"),
	Z("http://textures.minecraft.net/texture/90582b9b5d97974b11461d63eced85f438a3eef5dc3279f9c47e1e38ea54ae8d"),
	Ä("http://textures.minecraft.net/texture/15cd6db9ec3c7d9113e6dd49a16f99a326b9f594ce987f919559ac7dbd3b555"),
	Ö("http://textures.minecraft.net/texture/6edbb44c639b95308ffcdf8c4770dfe8b02d752dec4b3196f4a8f9ac2315393a"),
	Ü("http://textures.minecraft.net/texture/7b52b94c6516cbe461fea621d316cee0b875f0fbc239d25273e824b613e73dd4"),
	ArrowUp("http://textures.minecraft.net/texture/73cf166a826d855454ed4d4ea5fe33f3deeaa44ccaa99a348d39685ba71e1a4f"),
	ArrowDown("http://textures.minecraft.net/texture/38ca7615cb837f694e496ff8a9853cd7db5fd85529dcad98c78ba26c3e4f687"),
	ArrowRight("http://textures.minecraft.net/texture/1a4f68c8fb279e50ab786f9fa54c88ca4ecfe1eb5fd5f0c38c54c9b1c7203d7a"),
	ArrowLeft("http://textures.minecraft.net/texture/737648ae7a564a5287792b05fac79c6b6bd47f616a559ce8b543e6947235bce");
	
	
	private final String url;
	
	private WoodTextureUrlList(String url) {
	    this.url = url;
	}
	
	public String getURL() {
	    return url;
	}
}