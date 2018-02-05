package com.millonarios.MillonariosFC.ui.chat.messages;

import com.millonarios.MillonariosFC.app.manager.FirebaseManager;
import com.millonarios.MillonariosFC.models.firebase.Amigos;
import com.millonarios.MillonariosFC.ui.chat.chatview.adapter.ChatViewHolder;
import com.millonarios.MillonariosFC.ui.chat.friends.FriendsModelView;

/**
 * Created by Carlos on 22/01/2018.
 */

public class MessageModelView {

    private Amigos amigos;
    private Long idSender;
    private String idReceiver;
    private String apodo;
    private String foto;
    private FriendsModelView.STATUS status;
    private String content;
    private Long time;
    private boolean isMine;
    private boolean isPressed;
    private FirebaseManager.MsgTypes typeMsg;

    public MessageModelView() {

    }

    public Amigos getAmigos() {
        return amigos;
    }

    public void setAmigos(Amigos amigos) {
        this.amigos = amigos;
    }

    public MessageModelView(Long idSender, String apodo, String content, String foto, Long fecha, FriendsModelView.STATUS status, FirebaseManager.MsgTypes typeMsg, boolean isMine, Amigos amigos) {
        this.idSender = idSender;
        this.apodo = apodo;
        this.content = content;
        this.foto = foto;
        this.time = fecha;
        this.status = status;
        this.isMine = isMine;
        this.amigos = amigos;
        this.typeMsg = typeMsg;
    }

    public MessageModelView(Long idSender, String apodo, String content, String foto, FirebaseManager.MsgTypes typeMsg, boolean isMine) {
        this.idSender = idSender;
        this.apodo = apodo;
        this.content = content;
        this.foto = foto;
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

        return apodo;
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

    @Override
    public String toString() {
        return "MessageModelView{" +
                "idSender='" + idSender + '\'' +
                ", idReceiver='" + idReceiver + '\'' +
                ", apodo='" + apodo + '\'' +
                ", foto='" + foto + '\'' +
                ", status=" + status +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", isMine=" + isMine +
                '}';
    }
}
