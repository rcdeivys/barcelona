package com.BarcelonaSC.BarcelonaApp.utils.Constants;

/**
 * Created by Leonardojpr on 9/21/17.
 */

public interface Constant {

    String TOKEN_API = "token_api";

    String DATE_FORMAT = "MM-dd-yyyy";

    String DATE_FORMAT_WRITE = "yyyy-MM-dd HH:mm:ss.S";

    public static String BASE64 = "data:image/png;base64,";

    interface CanchaSize {

        int CANCHA_ALTO = 310;
        int CANCHA_LARGO = 210;
        int SIZE_IMAGEN = 30;
    }

    interface NewsType {
        String VIDEO = "Video";
        String NORMAL = "Normal";
        String INFOGRAFY = "Infografia";
        String GALERY = "Galeria";
        String STAT = "Stat";
    }

    interface Key {
        String LOGIN_STATUS = "login_status";
        String BANNER_TABLA = "tabla";
        String VIRTUAL_REALITY_SECTION = "Realidad virtual";
        String SHARE_VR = "videovr";
        String SHARE_NEWS = "noticia";
        String SHARE_GAME = "partido";
        String SHARE_PLAYER = "jugador_single";
        String IMG = "img";
        String WATERMARK = "watermark";
        String TITLE = "title";
        String ID = "id";
        String DESC_NEW = "descNew";
        String URL = "url";
        String PENDING = "PENDIENTE";
        String PROGRESS = "EN CURSO";
        String FINALIZED = "FINALIZADO";
        String USER = "user";
        String SUCCESS = "exito";
        String TYPE = "type";
        String PLAYER_ID = "playerId";
        String ACUMULATED = "acumulated";
        String LAST_GAME = "lastGame";
        String TOURNAMENTS = "tournaments";
        String CONFIGURATION = "configuration";
        String NOTIFICATION = "notification";
        String CUP = "CUP";
        String CUP_FB = "CUP_FB";
        String GAME_SUPPONNED = "gameSupponned";
        String GAME_FB = "gameFB";
        String MATCHES = "matches";
        String MONUMETAL_ID = "monumental_id";
        String SURVEY_ID = "survey_id";
        String HEADER = "HEADER";
        String SOCIAL = "SOCIAL";
        String FIRST_TIME_INTRO = "first_time_video";
        String FIRST_TIME_OATH = "first_time_oath";
        String SESSION = "session";
        String ID_CALENDARY_ELEVEN = "id_calendary_eleven";
        String LINEUP_URL = "lineup_url";
        String BANNERS = "Banners";


        String SECCION = "seccion";
        String ID_RESPUESTA = "id_respuesta";
        String SHOW_VOTES = "show_votes";

    }

    interface Preferences {

    }

    interface Network {
        interface Status {
            int SUCCESS = 200;
            int CREATED = 201;
            int BAD_REQUEST = 400;
            int UNAUTHORIZED = 401;
            int FORBIDDEN = 403;
            int NOT_FOUND = 404;
            int CONFLICT = 409;
        }
    }

    interface Menu {
        String PROFILE = "Profile";
        String Setting = "Setting";
        String NEWS = "News";
        String CALENDAR = "Calendar";
        String TABLE = "Table";
        String STATISTICS = "Statistics";
        String TEAM = "Team";
        String ALIGMENT = "Alignment";
        String VIRTUAL_REALITY = "Virtual Reality";
        String IN_LIVE = "In live";
        String YOUR_CHOOSE = "Your Choose";
        String GAME = "Game";
        String MAP = "Map";
        //String MILLIONARE_ACADEMY = "Millionaire Academy";
        //String FOOTBALL_BASE = "Footbool_base";
        String ONLINE_SHOP = "On-line shop";
        String WALL_AND_CHAT = "Wall And Chat";
        String MONUMENTAL = "Monumental";
    }

    interface Boolean {

        String TRUE = "1";
        String FALSE = "0";
    }

    interface ScreenAnalytcis {


    }

    interface Cup {
        String ID = "cup_id";
        String NAME = "cup_name";
    }

}
