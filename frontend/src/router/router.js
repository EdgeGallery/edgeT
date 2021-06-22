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
import Router from 'vue-router'
import Layout from '../pages/Layout.vue'
Vue.use(Router)

export default new Router({
  routes: [
    /** *********Routing For normal UI**************/
    {
      path: '/',
      name: 'scnario',
      component: () => import('../app/Scenario.vue')
    }, {
      path: '/index',
      redirect: '/',
      component: () => import('../app/Scenario.vue')
    },
    {
      path: '',
      name: 'layout',
      component: Layout,
      children: [
        {
          path: '/scenario',
          name: 'scenario',
          component: () => import('../app/Scenario.vue')
        },
        {
          path: '/testsuite',
          name: 'testsuite',
          component: () => import('../app/TestSuite.vue')
        },
        {
          path: '/testcase',
          name: 'testcase',
          component: () => import('../app/TestCase.vue')
        },
        {
          path: '/execution',
          name: 'execution',
          component: () => import('../app/Execution.vue')
        },
        {
          path: '/reports',
          name: 'reposts',
          component: () => import('../app/Reports.vue')
        },
        {
          path: '/profile',
          name: 'profile',
          component: () => import('../app/Profile.vue')
        },
        {
          path: '/tester',
          name: 'tester',
          component: () => import('../app/Tester.vue')
        }
      ]
    }
  ]
})
