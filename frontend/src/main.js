/*
 *  Copyright 2020 Huawei Technologies Co., Ltd.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import Vue from 'vue'
import App from './App.vue'
import router from './router/router.js'
import ElementUI from 'element-ui'
import '../src/assets/style/common.less'
import 'area-linkage-vue/dist/index.css'
import 'element-ui/lib/theme-chalk/index.css'
import 'element-ui/lib/theme-chalk/display.css'
import locale from 'element-ui/lib/locale/lang/en'
import i18n from './locales/i18n.js'
import VCharts from 'v-charts'
import { pcaa } from 'area-data-vue'
import AreaLinkageVue from 'area-linkage-vue'
Vue.prototype.showMessage = function (type, msg, time) {
  ElementUI.Message({
    showClose: true,
    type: type,
    message: msg,
    duration: time
  })
}
Vue.prototype.$pcaa = pcaa
Vue.use(AreaLinkageVue)
Vue.use(ElementUI, { locale })
Vue.use(VCharts)
Vue.config.productionTip = false

new Vue({
  router,
  i18n,
  render: h => h(App)
}).$mount('#app')
