import { Flex, Tag, Typography } from "antd";
import { Bubble } from "@ant-design/x";
import { AiSender } from "./AiSender.tsx";
import { ConversationPayload, getConversation } from "../../service/apis.ts";
import { useEffect, useRef, useState } from "react";
import { SettingOutlined, UserOutlined } from "@ant-design/icons";

interface AiMessageContentProps {
  conversationId: string;
}

export const AIMessageContent = (props: AiMessageContentProps) => {
  const [conversation, setConversation] = useState({} as ConversationPayload);

  const scrollRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    getConversation(props.conversationId).then((res) => {
      if (res.content) {
        setConversation(res.content);
      }
    });
  }, [props.conversationId]);

  useEffect(() => {
    if (scrollRef.current) {
      scrollRef.current.scrollTop = scrollRef.current.scrollHeight;
    }
  }, [conversation]);

  return (
    <Flex
      gap="middle"
      style={{
        height: "100%",
        width: "100%",
      }}
      vertical
    >
      <Flex
        ref={scrollRef}
        vertical
        gap={"middle"}
        style={{
          height: "90%",
          overflowY: "auto",
          paddingLeft: 30,
          paddingRight: 30,
        }}
      >
        {conversation.messages?.map((v) => {
          return v.type === "USER" ? (
            <Bubble
              content={
                <Typography.Text type="danger">{v.content}</Typography.Text>
              }
              placement={"end"}
              avatar={<UserOutlined />}
            ></Bubble>
          ) : (
            <Bubble
              content={<AssistantMsg content={v.content} />}
              placement={"start"}
              avatar={<SettingOutlined />}
            ></Bubble>
          );
        })}
      </Flex>
      <AiSender
        conversationId={props.conversationId}
        setConversation={setConversation}
        conversation={conversation}
      ></AiSender>
    </Flex>
  );
};

interface AssistantMsgProps {
  content: string;
}

const AssistantMsg = (props: AssistantMsgProps) => {
  const startTag = "<think>";
  const endTag = "</think>";

  const startIndex = props.content.indexOf(startTag);
  const endIndex = props.content.indexOf(endTag);

  let thinkContent = "";
  let outsideContent = "";

  if (startIndex !== -1 && endIndex !== -1) {
    thinkContent = props.content
      .substring(startIndex + startTag.length, endIndex)
      .trim();

    outsideContent = (
      props.content.substring(0, startIndex) +
      props.content.substring(endIndex + endTag.length)
    ).trim();
  }

  return (
    <Flex vertical gap={"middle"}>
      {thinkContent ? (
        <>
          <Tag color="#87d068">思考部分</Tag>
          <Typography.Text type="secondary" style={{ whiteSpace: "pre-line" }}>
            {thinkContent}
          </Typography.Text>
          <Tag color="#108ee9">回答</Tag>
          <Typography.Text style={{ whiteSpace: "pre-line" }}>
            {outsideContent}
          </Typography.Text>
        </>
      ) : (
        <>
          <Typography.Text style={{ whiteSpace: "pre-line" }}>
            {props.content}
          </Typography.Text>
        </>
      )}
    </Flex>
  );
};
