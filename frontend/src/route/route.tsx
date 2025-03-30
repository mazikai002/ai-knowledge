import { createBrowserRouter } from "react-router-dom";
import { Home } from "../pages/home/Home.tsx";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Home />,
    children: [],
  },
]);

export default router;
