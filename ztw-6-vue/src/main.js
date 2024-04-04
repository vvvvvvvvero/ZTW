import './assets/main.css'

import { createApp } from 'vue'
import {createRouter, createWebHistory} from "vue-router";

import App from './App.vue'
import Books from "@/components/Books.vue";
import Authors from "@/components/Authors.vue";
import Landings from "@/components/Landings.vue";


const router = createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/books', component: Books},
        { path: '/authors', component: Authors},
        { path: '/landings', component: Landings},
    ],
});

createApp(App)
    .use(router)
    .mount('#app')
