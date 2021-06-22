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
  <div class="my-comp-title">
    <div class="title1">
      <span>{{ this.title }}</span>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      default: function () {
        return ''
      }
    }
  },
  methods: {},
  mounted () {}
}

</script>
<style lang='less' scoped>
.my-comp-title{
  .title1{
    border-left: 2px solid #29ABE0;
    span{
      margin-left: 5px;
      font-size: 18px;
      font-weight: 800;
    }
  }
}

</style>
