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

const NavData = [
  {
    'id': '1.0',
    'icon': '',
    'name': 'Scenarios',
    'path': '/',
    'display': true,
    'children': [
      {
        'id': '1.1',
        'name': 'Scenario',
        'path': '/scenario',
        'display': false
      },
      {
        'id': '1.2',
        'name': 'Test Suite',
        'path': '/testsuite',
        'display': false
      },
      {
        'id': '1.3',
        'name': 'Test Case',
        'path': '/testcase',
        'display': false
      }
    ]
  },
  {
    'id': '1.4',
    'name': 'Run Time',
    'path': '/execution',
    'display': false,
    'children': [
      // {
      //  'id': '1.5',
      //  'name': 'Execution',
      //  'path': '/execution',
      //  'display': false
      // },
      {
        'id': '1.6',
        'name': 'Reports',
        'path': '/reports',
        'display': false
      }
    ]
  },
  {
    'id': '1.7',
    'name': 'Environment',
    'path': '/profile',
    'display': false,
    'children': [
      {
        'id': '1.8',
        'name': 'Profile',
        'path': '/profile',
        'display': false
      }
    ]
  },
  {
    'id': '1.9',
    'name': 'Tester',
    'path': '/tester',
    'display': false
  }
]

export default NavData
