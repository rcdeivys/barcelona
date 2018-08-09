package com.BarcelonaSC.BarcelonaApp.utils.Constants;

/**
 * Created by Leonardojpr on 9/21/17.
 */

public interface Constant {

    String TOKEN_API = "token_api";

    String DATE_FORMAT = "MM-dd-yyyy";

    String DATE_FORMAT_WRITE = "yyyy-MM-dd HH:mm:ss.S";

    public static String BASE64 = "data:image/png;base64,";

    interface Video {
        String CURRENT_POSITION = "currentPosition";
        String PLAY = "PLAY";
        String URL = "url";
    }

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
        String FIRST_TIME_DORADO = "first_time_dorado";
        String CUP = "CUP";
        String CUP_FB = "CUP_FB";
        String GAME_SUPPONNED = "gameSupponned";
        String MULTIMEDIA_VIDEO = "multimediaVideo";
        String MULTIMEDIA_ONLINE = "multimediaOnLine";
        String GAME_FB = "gameFB";
        String MATCHES = "matches";
        String MONUMETAL_ID = "monumental_id";
        String SURVEY_ID = "survey_id";
        String HEADER = "HEADER";
        String SOCIAL = "SOCIAL";
        String HINCHA_COUNTER = "HINCHA_COUNTER";
        String FIRST_TIME_INTRO = "first_time_video";
        String FIRST_TIME_OATH = "first_time_oath";
        String SESSION = "session";
        String ID_CALENDARY_ELEVEN = "id_calendary_eleven";
        String LINEUP_URL = "lineup_url";
        String BANNERS = "Banners";
        String IS_SOCIAL = "isSocial";

        String SECCION = "seccion";
        String SECCION_PARTIDO = "seccion_calendar_partido" ;
        String ID_RESPUESTA = "id_respuesta";
        String SHOW_VOTES = "show_votes";

        String DORADO_CONFIGURATION = "dorado_configuration";
        String DORADO_STATUS_VIEW = "dorado_status_view";
        String DORADO_STATUS_SUSCRIPCION = "dorado_id_suscripcion";

        String POPUP_INTERNO = "INTERNO";
        String POPUP_EXTERNO = "EXTERNO";
        String POPUP_SECCION = "SECCION";
        String POPUP_UPDATE = "UPDATE";
        String SECCION_SELECTED = "seccion_Selected";
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

        String PROFILE = "profile";
        String Setting = "Setting";
        String NEWS = "noticias";
        String CALENDAR = "calendario";
        String TABLE = "tabla";
        String STATISTICS = "estadisticas";
        String TEAM = "equipo";
        String ALIGMENT = "alineacion";
        String MONUMENTAL = "monumental";
        String VIRTUAL_REALITY = "realidad_virtual";
        String IN_LIVE = "In live";
        String VIDEOS = "videos";
        String YOUR_CHOOSE = "tu_escoges";
        String GAME = "Game";
        String MILLIONARE_ACADEMY = "academia";
        String FOOTBALL_BASE = "futbol_base";
        String ONLINE_SHOP = "tienda_virtual";
        String WALL_AND_CHAT = "muro";
        String CHAT = "chat";


        String TITLE = "titulo";
        String ITEM = "item";
        String VERSION = "version";

        /*String PROFILE = "profile";
        String Setting = "Setting";
        String NEWS = "news";
        String CALENDAR = "calendar";
        String TABLE = "table";
        String STATISTICS = "statistics";
        String TEAM = "team";
        String ALIGMENT = "line_up";
        String VIRTUAL_REALITY = "virtual_reality";
        String IN_LIVE = "live";
        String YOUR_CHOOSE = "you_choose";
        String GAME = "games";
        String MILLIONARE_ACADEMY = "academy";
        String FOOTBALL_BASE = "football_base";
        String ONLINE_SHOP = "store";
        String WALL_AND_CHAT = "muro";*/

        String MAP = "geolocalizacion";

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

    interface Seccion {
        String Id_Post = "id_post";
        String MURO = "muro";
        String NOTICIAS = "news";
        String CHAT= "chat";
    }

    interface Analytics {
        String BANNER = "BANNER";
        String CALENDAR = "CALENDAR";
        String CHAT = "CHAT";
        String ERROR = "ERROR";
        String GAME = "GAME";
        String INTRO_VIDEO = "INTRO VIDEO";
        String LINEUP = "LINEUP";
        String LOGIN = "LOGIN";
        String MAP = "MAP";
        String MENU = "MENU";
        String MULTIMEDIA = "MULTIMEDIA";
        String NEWS = "NEWS";
        String NOTIFICATION = "NOTIFICATION";    
        String PLAYER = "PLAYER";
        String POLLS = "POLLS";
        String PROFILE = "PROFILE";
        String REGISTER = "REGISTER";    
        String STATISTICS = "STATS";
        String STORE = "STORE";
        String TABLE = "TABLE";
        String TEAM = "TEAM";
        String VIRTUAL_REALITY = "VIRTUAL REALITY";
        String WALL = "WALL";
    }
    
    interface ActionTags {
        String Clicked = "Clicked";
        String Scrolled = "Scrolled";
        String LeftSwipe = "Left Swipe";
        String RightSwipe = "Right Swipe";
    }

    interface ChatTags {
        String Groups = "Groups";
        String Messages = "Messages";
    }

    interface LineupTags {
        String Ideal = "Ideal";
        String Armar = "Armar";
    }

    interface LoginTags {
        String Reset = "Reset";
    }

    interface MapTags {
        String Search = "Map Search";
    }

    interface MultimediaTags {
        String VideoFeed = "VideoFeed";
        String Live = "Live";
    }

    interface NewsTags {
        String Infografy = "Infografy";
        String Normal = "Normal";
        String Video = "Video";
        String VR = "VR";
        String Gallery = "Gallery";
        String GalleryPhoto = "GalleryPhoto";
    }

    interface NotificationTags {
        String Received = "Received";
        String Clicked = "Clicked";
        String Dismissed = "Dismissed";
    }

    interface PlayerTags {
        String SocialMedia = "SocialMedia";
    }

    interface PollTags {
        String Vote = "Vote";
        String Ranking = "Ranking";
    }

    interface ProfileTags {
        String User = "";
        String Info = "Info";
        String Card = "Card";
        String CardFail = "CardFail";
        String Photo = "Photo";
        String Terms = "Terms";
        String Logout = "Log Out";
    }

    interface RegisterTags {
        String Facebook = "Facebook";
        String Google = "Google";
        String Terms = "Terms";
    }

    interface StatsTags {
        String Results = "Results";
        String Positions = "Positions";
        String Ranking = "Ranking";
        String Goals = "Goals";
        String Planteles = "Planteles";
        String Teams = "Teams";
        String Players = "Players";
        String CompareTeams = "CompareTeams";
        String ComparePlayers = "ComparePlayers";
        String Gamecast = "Gamecast";
    }

    interface SocialMediaTags {
        String Facebook = "Facebook";
        String Instagram = "Instagram";
        String Twitter = "Twitter";
        String YouTube = "YouTube";
    }

    interface TableTags {
        String Simulator = "Simulator";
    }

    interface TeamTags {
        String Roster = "Roster";
        String Search = "Search";
        String Convocados = "Convocados";
        String HighlightsLatest = "HighlightsLatest";
        String HighlightsAllTime = "HighlightsAllTime";
    }

    interface WallTags {
        String Post = "Post";
        String Profile = "Profile";
        String Comments = "Comments";
    }
}
