import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

export default defineConfig({
  plugins: [react()],
  server: {
    host: true,
    proxy: {
      "/api/": {
        target: "http://0.0.0.0:8080",
        changeOrigin: true,
      },
    },
  },
});
