import { useEffect, useState } from "react";
import { DocPayload, getDocs } from "../../service/apis.ts";
import { Flex, Typography } from "antd";
import { FileOutlined } from "@ant-design/icons";

export const FeishuDocs = () => {
  const [docs, setDocs] = useState([] as DocPayload[]);

  useEffect(() => {
    getDocs().then((res) => {
      if (res.content) {
        setDocs(res.content);
      }
    });
  }, []);

  const openLink = (url: string) => {
    window.open(url);
  };

  return (
    <div>
      <h3>飞书知识库文档列表</h3>
      <Flex vertical gap={"middle"}>
        {docs &&
          docs.map((value) => {
            return (
              <Flex
                gap={"middle"}
                style={{ overflowY: "auto", cursor: "pointer" }}
                onClick={() => openLink(value.url)}
              >
                <FileOutlined></FileOutlined>
                <Typography.Link href={value.url} target="_blank">
                  {value.name}
                </Typography.Link>
              </Flex>
            );
          })}
      </Flex>
    </div>
  );
};
