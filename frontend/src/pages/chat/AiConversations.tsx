import { Button, Flex, Input, Modal } from "antd";
import { Conversations, ConversationsProps } from "@ant-design/x";
import { DeleteOutlined, EditOutlined, PlusOutlined } from "@ant-design/icons";

import type { Conversation } from "@ant-design/x/es/conversations/interface";
import {
  ConversationMenuPayload,
  createConversation,
  deleteConversation,
  editConversation,
} from "../../service/apis.ts";
import { useState } from "react";

interface ConversationProps {
  conversations: ConversationMenuPayload[];
  setConversations: (conversations: ConversationMenuPayload[]) => void;
  conversationId: string;
  setConversationId: (s: string) => void;
  onRemove: (s: string) => void;
  onTitleUpdate: (convId: string, title: string) => void;
}

export const AiConversations = (props: ConversationProps) => {
  const {
    conversations,
    setConversations,
    conversationId,
    setConversationId,
    onRemove,
    onTitleUpdate,
  } = props;

  const [isModalOpen, setIsModalOpen] = useState(false);

  const [onUpdateConvId, setOnUpdateConvId] = useState("");
  const [updateTitle, setUpdateTitle] = useState("");

  const menuConfig: ConversationsProps["menu"] = (conversation) => ({
    items: [
      {
        label: "修改名称",
        key: "edit",
        icon: <EditOutlined />,
        onClick: () => {
          setIsModalOpen(true);
          setOnUpdateConvId(conversation.key);
        },
      },
      {
        label: "删除会话",
        key: "del",
        icon: <DeleteOutlined />,
        danger: true,
        onClick: () => {
          deleteConversation(conversation.key).then((res) => {
            if (res.success) {
              onRemove(conversation.key);
            }
          });
        },
      },
    ],
  });

  const onCreate = () => {
    createConversation().then((res) => {
      const conv = res.content;
      if (conv) {
        setConversations([
          ...conversations,
          { conversationId: conv.conversationId, title: conv.title },
        ]);
        onConversationChange(conv.conversationId);
      }
    });
  };

  const onConversationChange = (value: string) => {
    setConversationId(value);
  };

  const handleTitleUpdate = () => {
    editConversation(onUpdateConvId, updateTitle).then((res) => {
      if (res.success) {
        onTitleUpdate(onUpdateConvId, updateTitle);
        setIsModalOpen(false);
      }
    });
  };

  return (
    <Flex style={{ height: "100%", background: "white" }} vertical>
      <Conversations
        defaultActiveKey={conversationId}
        activeKey={conversationId}
        menu={menuConfig}
        items={conversations.map((value) => {
          return {
            key: value.conversationId,
            label: value.title,
          } as Conversation;
        })}
        onActiveChange={onConversationChange}
      />
      <Flex align={"center"} justify={"center"}>
        <Button
          style={{ width: 160 }}
          icon={<PlusOutlined />}
          onClick={onCreate}
        >
          新建会话
        </Button>
      </Flex>
      <Modal
        title="修改会话名称"
        open={isModalOpen}
        onOk={handleTitleUpdate}
        onCancel={() => setIsModalOpen(false)}
      >
        <Input
          onChange={(e) => {
            setUpdateTitle(e.target.value);
          }}
        />
      </Modal>
    </Flex>
  );
};
