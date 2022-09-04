package com.sing.herostory.cmdHandler;

import com.sing.herostory.msg.GameMsgProtocol;
import com.sing.herostory.rank.RankItem;
import com.sing.herostory.rank.RankService;
import io.netty.channel.ChannelHandlerContext;

public class GetRankCmdHandler implements ICmdHandler<GameMsgProtocol.GetRankCmd> {
    @Override
    public void handle(ChannelHandlerContext channelHandlerContext, GameMsgProtocol.GetRankCmd getRankCmd) {
        RankService.getInstance().getRank((rankItems) -> {
            GameMsgProtocol.GetRankResult.Builder resultBuilder = GameMsgProtocol.GetRankResult.newBuilder();
            for (RankItem rankItem : rankItems) {
                GameMsgProtocol.GetRankResult.RankItem.Builder rankItemBuilder = GameMsgProtocol.GetRankResult.RankItem.newBuilder();
                rankItemBuilder.setRankId(rankItem.getRankId())
                        .setUserId(rankItem.getUserId())
                        .setUserName(rankItem.getUserName())
                        .setHeroAvatar(rankItem.getHeroAvatar())
                        .setWin(rankItem.getWin());
                resultBuilder.addRankItem(rankItemBuilder.build());
            }
            channelHandlerContext.writeAndFlush(resultBuilder.build());
            return null;
        });
    }
}
