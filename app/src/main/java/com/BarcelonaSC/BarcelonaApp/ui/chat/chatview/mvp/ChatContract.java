package com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.mvp;

import android.net.Uri;

import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Conversacion;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Miembro;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatmodels.FriendsModelView;
import com.BarcelonaSC.BarcelonaApp.ui.chat.messages.MessageModelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pedro Gomez on 29/01/2018.
 */

public class ChatContract {

    static class ModelResultListener {

        public interface OnSendMessages {
            void onSendMessageSuccess(MessageModelView message);

            void onSendMessageSuccess(ArrayList<MessageModelView> messages);

            void onSendMessageFailed();

            void multimediaSuccess();

            void multimediaFailed();
        }

        public interface OnChatReport {
            void onReportSuccess();

            void onReportFailed();
        }


        public interface OnLoadMessages {
            void onLoadMessageSuccess(ArrayList<MessageModelView> messages);

            void onLoadMessageFailed();

            void onLoadPaginateMessage(ArrayList<MessageModelView> mensajes);
        }

        public interface OnDeleteMember {

            void onDeleteMyUserSuccesS(List<FriendsModelView> friends);

            void onDeleteMemberSucces(List<FriendsModelView> friends);

            void onDeleteMembersFailed();

        }

    }

    public interface Presenter extends MVPContract.Presenter<View> {

        void toSendMessage(String idConv, Long idSender, List<Miembro> idReceiver, String msgContent, Uri uri, String idGrupo);

        void loadMessagesPrivate(Conversacion conversacion);

        void loadMessagesGroup(String id_chat);

    }

    public interface View {

        void deleteMember();

        void updateMesage(List<MessageModelView> messageModelViews);

        void onMissingParams();

        void refresh();

        void blockUser();

        void onReportFailed();

        void onReportSuccess();

        void multimediaSuccess();

        void multimediaFailed();

        void cancelPagination();
    }

}
