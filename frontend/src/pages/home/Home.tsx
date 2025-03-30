import React, { useEffect, useState } from "react";
import { Layout, theme } from "antd";
import { AIMessageContent } from "../chat/AiMessageContent.tsx";
import { AiConversations } from "../chat/AiConversations.tsx";
import {
  ConversationMenuPayload,
  getConversationMenus,
} from "../../service/apis.ts";
import { FeishuDocs } from "../chat/FeishuDocs.tsx";

const { Header, Content, Sider } = Layout;

export const Home: React.FC = () => {
  const {
    token: { colorBgContainer },
  } = theme.useToken();

  const [conversations, setConversations] = useState(
    [] as ConversationMenuPayload[],
  );
  const [conversationId, setConversationId] = useState("");
  useEffect(() => {
    getConversationMenus().then((res) => {
      if (res.content) {
        setConversations(res.content);
        setConversationId(res.content[0].conversationId);
      }
    });
  }, []);

  const onConversationRemove = (removedId: string) => {
    const updates = conversations.filter(
      (value) => value.conversationId !== removedId,
    );
    setConversations(updates);
    if (conversationId === removedId && conversations.length > 0) {
      setConversationId(conversations[0].conversationId);
    } else {
      setConversationId(conversationId);
    }
  };

  const onTitleUpdate = (convId: string, title: string) => {
    setConversations(
      conversations.map((value) =>
        value.conversationId === convId ? { ...value, title: title } : value,
      ),
    );
  };

  return (
    <Layout style={{ height: "100%" }}>
      <Header>
        <div style={{ color: "white", fontSize: "large" }}>企业AI知识库</div>
      </Header>
      <Layout>
        <Sider width={"20%"}>
          {conversationId && (
            <AiConversations
              conversations={conversations}
              conversationId={conversationId}
              setConversationId={setConversationId}
              setConversations={setConversations}
              onRemove={onConversationRemove}
              onTitleUpdate={onTitleUpdate}
            ></AiConversations>
          )}
        </Sider>
        <Layout style={{ padding: "0 24px 24px", width: "60%" }}>
          <Content
            style={{
              padding: 24,
              background: colorBgContainer,
            }}
          >
            {conversationId && (
              <AIMessageContent conversationId={conversationId} />
            )}
          </Content>
        </Layout>
        <Layout
          style={{
            padding: 24,
            background: colorBgContainer,
            width: "20%",
          }}
        >
          <Content>
            <FeishuDocs />
          </Content>
        </Layout>
      </Layout>
    </Layout>
  );
};
