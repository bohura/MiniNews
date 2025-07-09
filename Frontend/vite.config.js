import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig(
  () => {
    return{
      plugins: [vue()],

    server: {
      port: 8001,
      open: true,
      proxy: {
        '/app-dev': {
          target: 'http://localhost:8080/',
          changeOrigin: true,
          rewrite: (path) => path.replace(/^\/app-dev/, '')
        }
      }
    }
    }
  }
 )
