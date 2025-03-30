import { useEffect, useState } from "react";
import { Sender } from "@ant-design/x";
import { chat, ConversationPayload } from "../../service/apis.ts";

interface AiSenderProps {
  conversationId: string;
  setConversation: (conversation: ConversationPayload) => void;
  conversation: ConversationPayload;
}

export const AiSender = (props: AiSenderProps) => {
  const [value, setValue] = useState<string>("");
  const [loading, setLoading] = useState<boolean>(false);

  const { conversationId, conversation, setConversation } = props;

  useEffect(() => {
    if (loading) {
      setConversation({
        ...conversation,
        messages: [...conversation.messages, { type: "USER", content: value }],
      });
      chat(conversationId, value).then((res) => {
        setConversation({
          ...conversation,
          messages: [
            ...conversation.messages,
            { type: "USER", content: value },
            { type: "ASSISTANT", content: res.content },
          ],
        });
        setLoading(false);
        setValue("");
      });
    }
  }, [loading]);

  return (
    <Sender
      loading={loading}
      value={value}
      onChange={(v) => {
        setValue(v);
      }}
      onSubmit={() => {
        setLoading(true);
      }}
      onCancel={() => {
        setLoading(false);
      }}
    />
  );
};
