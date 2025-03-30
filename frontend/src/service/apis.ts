import { http, HttpResp } from "./global.ts";
import { MessageType } from "./common.ts";

export interface ConversationMenuPayload {
  conversationId: string;
  title: string;
}

export interface ConversationContent {
  type: MessageType;
  content: string;
}

export interface ConversationPayload {
  conversationId: string;
  title: string;
  messages: ConversationContent[];
}

export function getConversationMenus() {
  return http.get<
    ConversationMenuPayload[],
    HttpResp<ConversationMenuPayload[]>
  >("conversation/list");
}

export function getConversation(conversationId: string) {
  return http.get<ConversationPayload, HttpResp<ConversationPayload>>(
    "conversation/get",
    {
      params: { conversationId: conversationId },
    },
  );
}

export function createConversation() {
  return http.post<ConversationMenuPayload, HttpResp<ConversationMenuPayload>>(
    "conversation/create",
  );
}

export function editConversation(conversationId: string, title: string) {
  return http.put<unknown, HttpResp>(
    "conversation/edit",
    {
      conversationId: conversationId,
      title: title,
    },
    {
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
      },
    },
  );
}

export function deleteConversation(conversationId: string) {
  return http.delete<unknown, HttpResp>("conversation/del", {
    params: { conversationId: conversationId },
  });
}

export function chat(conversationId: string, message: string) {
  return http.get<string, HttpResp<string>>("conversation/chat", {
    params: { conversationId: conversationId, message: message },
  });
}

export interface DocPayload {
  docId: string;
  name: string;
  url: string;
}

export function getDocs() {
  return http.get<DocPayload[], HttpResp<DocPayload[]>>("doc/list");
}
