package com.sing.herostory;

import com.google.protobuf.Message;
import com.sing.herostory.msg.GameMsgProtocol;

/**
 * 消息识别器
 * @author songbo
 * @since 2022-08-31
 */
public class GameMsgRecognizer {
    private GameMsgRecognizer(){

    }

    /**
     * 获取消息构建者(解码）
     * @param msgCode
     * @return
     */
    public static Message.Builder getBuilderByMsgCode(int msgCode){
        Message.Builder msgBuilder = null;
        switch (msgCode) {
            case GameMsgProtocol.MsgCode.USER_ENTRY_CMD_VALUE:
                msgBuilder = GameMsgProtocol.UserEntryCmd.newBuilder();
                break;
            case GameMsgProtocol.MsgCode.WHO_ELSE_IS_HERE_CMD_VALUE:
                msgBuilder = GameMsgProtocol.WhoElseIsHereCmd.newBuilder();
                break;
            case GameMsgProtocol.MsgCode.USER_MOVE_TO_CMD_VALUE:
                msgBuilder = GameMsgProtocol.UserMoveToCmd.newBuilder();
                break;
        }
        return msgBuilder;
    }

    /**
     * 获取消息code（编码）
     * @param msg
     * @return
     */
    public static int getMsgCodeByClazz(Object msg){
        int msgCode = -1;
        if(msg instanceof GameMsgProtocol.UserEntryResult){
            msgCode = GameMsgProtocol.MsgCode.USER_ENTRY_RESULT_VALUE;
        }else if(msg instanceof GameMsgProtocol.WhoElseIsHereResult){
            msgCode = GameMsgProtocol.MsgCode.WHO_ELSE_IS_HERE_RESULT_VALUE;
        }else if (msg instanceof GameMsgProtocol.UserMoveToResult){
            msgCode = GameMsgProtocol.MsgCode.USER_MOVE_TO_RESULT_VALUE;
        }else if (msg instanceof GameMsgProtocol.UserQuitResult){
            msgCode = GameMsgProtocol.MsgCode.USER_QUIT_RESULT_VALUE;
        }
        return msgCode;

    }
}
