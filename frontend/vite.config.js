import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      // React에서 /api 로 호출하면, Vite가 Spring(8080)으로 프록시
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
    },
  },
})