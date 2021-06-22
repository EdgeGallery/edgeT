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
  <div>
    <el-dialog
      title="Add Profile"
      :visible.sync="visible"
    >
      <el-form
        label-width="120px"
      >
        <el-col
          :span="12"
        >
          <el-form-item
            label="Profile Name"
          >
            <el-input
              size="small"
              style="text-align:center"
              v-model="pData.profileName"
            />
          </el-form-item>
        </el-col>
      </el-form>
      <div
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          id="cancelBtn"
          @click="visible = false"
        >
          Cancel
        </el-button>
        <el-button
          id="confirmBtn"
          type="primary"
          @click="addProfile()"
          :loading="loading"
        >
          Confirm
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { profile } from '../tools/request.js'
export default {
  name: 'AddProfile',
  props: {
    visible: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      pData: {
        profileName: '',
        properties: [
          {
            'scenario': 's1',
            'testSuiteName': 'ts1',
            'testCaseName': 'tc1',
            'inputParameterName': 'p1',
            'value': 'v1'
          },
          {
            'scenario': 's2',
            'testSuiteName': 'ts1',
            'inputParameterName': 'p1',
            'value': 'v1'
          },
          {
            'scenario': 's1',
            'testCaseName': 'tc1',
            'inputParameterName': 'p2',
            'value': 'v2'
          },
          {
            'inputParameterName': 'global-param-1',
            'value': 'v1'
          },
          {
            'inputParameterName': 'p2',
            'value': null
          },
          {
            'inputParameterName': null,
            'value': 'v1'
          }
        ]
      }
    }
  },
  watch: {
    visible (val) {
      this.$emit('getDialogStatus', val)
    }
  },
  mounted () {
  },
  methods: {
    addProfile () {
      profile.addProfile(this.pData).then(res => {
        this.visible = false
        this.$emit('getProfile')
      }, error => {
        if (error) {
          this.$parent.errorMessage('Add profile failed.')
        }
      })
    }
  }
}
</script>
<style>
</style>
