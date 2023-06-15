import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import App from './App.vue'
import router from './router'
import axios from "axios";


const app = createApp(App)

axios.defaults.baseURL='http://127.0.0.1:8080'

app.use(createPinia())
app.use(router)
app.mount('#app')

