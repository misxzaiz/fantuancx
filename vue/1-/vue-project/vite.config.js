import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  pages: {
    index: {
      entry: './src/main.js',
      template: './src/index.html',
      title: '主页'
    },
    user: {
      entry: './src/pages/user/main.js',
      template: './src/pages/user/index.html',
      title: '用户'
    }
  }
})
