package com.kanzhun.okhttp.bean;

import java.util.List;

/**
 * Author: ZhouYou
 * Date: 2017/6/6.
 */
public class MusicBean {

    /**
     * rating : {"max":10,"average":"9.6","numRaters":3280,"min":0}
     * author : [{"name":"TVサントラ"}]
     * alt_title :
     * image : https://img3.doubanio.com/spic/s3587212.jpg
     * tags : [{"count":732,"name":"OST"},{"count":567,"name":"银魂"},{"count":336,"name":"銀魂"},{"count":335,"name":"日本"},{"count":255,"name":"ACG"},{"count":193,"name":"Anime"},{"count":136,"name":"J-POP"},{"count":133,"name":"动漫"}]
     * mobile_link : https://m.douban.com/music/subject/3526484/
     * attrs : {"publisher":["Aniplex (music)"],"singer":["TVサントラ"],"discs":["2"],"pubdate":["2009-03-25"],"title":["銀魂BEST 【期間生産限定盤】"],"media":["CD"],"tracks":["第1期OP「Pray」Tommy heavenly6\n第1期ED「風船ガム」キャプテンストライダム\n第2期ED「MR.RAINDROP」amplified\n第2期OP「遠い匂い」YO-KING\n第3期ED「雪のツバサ」redballoon\n第4期ED「キャンディ・ライン」高橋 瞳\n第5期OP「銀色の空」redballoon\n第5期ED「修羅」DOES\n第6期ED「奇跡」シュノーケル\n第4期OP「かさなる影」Hearts Grow\n第7期ED「SIGNAL」KELUN\n第8期ED「Speed of flow」THE RODEO CARBURETTOR\n第5期OP「曇天」DOES\n第9期ED「sanagi」POSSIBILITY\n第10期ED「This world is yours」プリングミン"],"version":["CD+DVD","Limited Edition"]}
     * title : 銀魂BEST 【期間生産限定盤】
     * alt : https://music.douban.com/subject/3526484/
     * id : 3526484
     */
    private RatingBean rating;
    private String alt_title;
    private String image;
    private String mobile_link;
    private AttrBean attrs;
    private String title;
    private String alt;
    private String id;
    private List<AuthorBean> author;
    private List<TagBean> tags;
}
