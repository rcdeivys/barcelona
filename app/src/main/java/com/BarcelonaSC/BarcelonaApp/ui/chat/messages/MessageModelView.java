package com.BarcelonaSC.BarcelonaApp.ui.chat.messages;

import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Amigos;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Miembro;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatmodels.FriendsModelView;

/**
 * Created by Carlos on 22/01/2018.
 */

public class MessageModelView {

    private String id;
    private Amigos amigos;
    private Long idSender;
    private String idReceiver;
    private String apodo;
    private String foto;
    private FriendsModelView.STATUS status;
    private String content;
    private Long time;
    private boolean isMine;
    private String create_date;
    private boolean isPressed;
    private FirebaseManager.MsgTypes typeMsg;
    private String thunbmailVideoUrl;


    public MessageModelView() {

    }

    public Amigos getAmigos() {
        return amigos;
    }

    public void setAmigos(Amigos amigos) {
        this.amigos = amigos;
    }

    public MessageModelView(Long idSender, String apodo, String content, String foto, Long fecha, FriendsModelView.STATUS status, FirebaseManager.MsgTypes typeMsg, boolean isMine, Amigos amigos, String create_date) {
        this.idSender = idSender;
        this.apodo = apodo;
        this.content = content;
        this.foto = foto;
        this.time = fecha;
        this.status = status;
        this.isMine = isMine;
        this.amigos = amigos;
        this.typeMsg = typeMsg;
        this.create_date = create_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MessageModelView(String id, Long idSender, String apodo, String content, String foto, FirebaseManager.MsgTypes typeMsg, boolean isMine, String create_date, Long time, String thunbmailVideoUrl) {
        this.id = id;
        this.idSender = idSender;
        this.apodo = apodo;
        this.content = content;
        this.foto = foto;
        this.isMine = isMine;
        this.typeMsg = typeMsg;
        this.create_date = create_date;
        this.time = time;
        this.thunbmailVideoUrl = thunbmailVideoUrl;
    }

    public void setMember(Miembro miembro) {
        this.apodo = miembro.getApodo();
        this.foto = miembro.getFoto();
        this.create_date = miembro.getCreated_at();
    }

    public MessageModelView(String id,Long idSender, String content, FirebaseManager.MsgTypes typeMsg, boolean isMine) {
        this.id = id;
        this.idSender = idSender;
        this.content = content;
        this.isMine = isMine;
        this.typeMsg = typeMsg;

    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public Long getIdSender() {
        return idSender;
    }

    public void setIdSender(Long idSender) {
        this.idSender = idSender;
    }

    public String getApodo() {
        if (apodo == null)
            return "PRUEBAA";
        return apodo;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public FriendsModelView.STATUS getStatus() {
        return status;
    }

    public void setStatus(FriendsModelView.STATUS status) {
        this.status = status;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getIdReceiver() {
        return idReceiver;
    }

    public void setIdReceiver(String idReceiver) {
        this.idReceiver = idReceiver;
    }


    public boolean isPressed() {
        return isPressed;
    }

    public void setPressed(boolean pressed) {
        isPressed = pressed;
    }

    public FirebaseManager.MsgTypes getTypeMsg() {
        return typeMsg;
    }

    public void setTypeMsg(FirebaseManager.MsgTypes typeMsg) {
        this.typeMsg = typeMsg;

    }

    public String getThunbmailVideoUrl() {
        return thunbmailVideoUrl;
    }

    public void setThunbmailVideoUrl(String thunbmailVideoUrl) {
        this.thunbmailVideoUrl = thunbmailVideoUrl;
    }



    @Override
    public String toString() {
        return "MessageModelView{" +
                "amigos=" + amigos +
                ", idSender=" + idSender +
                ", idReceiver='" + idReceiver + '\'' +
                ", apodo='" + apodo + '\'' +
                ", foto='" + foto + '\'' +
                ", status=" + status +
                ", content='" + content + '\'' +
                ", time=" + time +
                ", isMine=" + isMine +
                ", create_date='" + create_date + '\'' +
                ", isPressed=" + isPressed +
                ", typeMsg=" + typeMsg +
                '}';
    }
}
