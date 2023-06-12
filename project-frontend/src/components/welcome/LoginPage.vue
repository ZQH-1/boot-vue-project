<template>
      <div style="text-align: center;margin: 0 20px">
        <div style="margin-top: 150px">
          <div style="font-size: 25px;font-weight: bold">登陆</div>
          <div style="font-size: 14px;color: gray;">请输入账号密码登陆</div>
        </div>
        <div style="margin-top: 50px">
          <el-input type="text" placeholder="账户名/邮箱" v-model='form.username' >
            <template #prefix>
              <el-icon><User /></el-icon>
            </template>
          </el-input>
          <el-input type="password" placeholder="密码" style="margin-top: 10px" v-model="form.password">
            <template #prefix>
              <el-icon><Lock /></el-icon>
            </template>
          </el-input>
        </div>
        <div style="margin-top: 5px">
          <el-row>
            <el-col :span="12" style="text-align: left">
              <el-checkbox v-model="form.remember" label="记住我" />
            </el-col>
            <el-col :span="12" style="text-align: right;font-size: 14px">
              <el-link>忘记密码？</el-link>
            </el-col>
          </el-row>

        </div>
        <div style="margin-top: 40px">
          <el-button @click="login()" type="success" style="width: 270px" plain>马上登陆</el-button>
        </div>
        <el-divider>
          <span style="color: gray" >没有账号？</span>
        </el-divider>
        <div>
          <el-button type="danger" @click="router.push('/register')" style="width: 270px" plain>立即注册</el-button>
        </div>
      </div>

</template>

<script setup>
import {ElMessage} from "element-plus";

const form=reactive({
  username:'',
  password:'',
  remember:false
})
const login = () => {
  if(!form.username ||!form.password) {
    ElMessage.warning('用户名/密码不能为空！');
  }else{
    post("/api/auth/login",{
      username:form.username,
      password:form.password,
      remember:form.remember
    },(message)=>{
      ElMessage.success(message)
      router.push('/index')
    },(message)=>{
      ElMessage.warning(message)
    },(message)=>{
      ElMessage.error(message)
    })
  }

}
import {User,Lock} from "@element-plus/icons-vue";


import {reactive} from "vue";
import router from "../../router";
import {post} from "../../net";
</script>

<style scoped>

</style>
