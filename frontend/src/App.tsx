import "./App.css";
import { RouterProvider } from "react-router";
import router from "./route/route.tsx";
import { ConfigProvider } from "antd";

const App = () => {
  return (
    <ConfigProvider>
      <RouterProvider router={router}></RouterProvider>
    </ConfigProvider>
  );
};

export default App;
