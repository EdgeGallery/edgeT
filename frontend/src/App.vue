<!--
  -  Copyright 2020 Huawei Technologies Co., Ltd.
  -
  -  Licensed under the Apache License, Version 2.0 (the "License");
  -  you may not use this file except in compliance with the License.
  -  You may obtain a copy of the License at
  -
  -      http://www.apache.org/licenses/LICENSE-2.0
  -
  -  Unless required by applicable law or agreed to in writing, software
  -  distributed under the License is distributed on an "AS IS" BASIS,
  -  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  -  See the License for the specific language governing permissions and
  -  limitations under the License.
  -->

<template>
  <div id="app">
    <Navbar class="xs-display-none display-block " />
    <router-view />
  </div>
</template>

<script>
import Navbar from './components/Nav'
export default {
  name: 'App',
  components: {
    Navbar
  },
  beforeCreate () {
    if (sessionStorage.getItem('before_route')) {
      this.$router.push(sessionStorage.getItem('before_route'))
    }
  }
}
</script>

<style>
#app {
  font-family: Huaweisans, Arial, Microsoft YaHei, FZLTXHJW, Microsoft JhengHei, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  background: #f5f5f5;
  background-size:cover;
  overflow: auto;
}
.display-none{
  display: none
}
.display-block{
  display:block
}

@media(max-width:767.98px) {
  .xs-display-none {
    display: none;
  }
}
</style>
