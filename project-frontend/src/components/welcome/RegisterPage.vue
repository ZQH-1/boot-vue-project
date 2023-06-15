<template>
  <div style="text-align: center;margin: 0 20px">
    <div style="margin-top: 100px">
      <div style="font-size: 25px;font-weight: bold">注册</div>
      <div style="font-size: 14px;color: gray;">请输入填写下方账号资料</div>
    </div>
    <el-divider></el-divider>
    <div style="margin-top: 50px">
      <el-form :model="form" :rules="rules" @validate="mailValid" ref="formRef">
        <el-form-item prop="username">
          <el-input v-model="form.username" minlength="3" maxlength="8" type="text" placeholder="用户名">
            <template #prefix>
              <el-icon><User /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" type="password" minlength="6" maxlength="16" placeholder="密码" >
            <template #prefix>
              <el-icon><Lock /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password_repeat">
          <el-input v-model="form.password_repeat" type="password" minlength="6" maxlength="16" placeholder="重复密码" >
            <template #prefix>
              <el-icon><Lock /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="email">
          <el-input v-model="form.email" type="email" placeholder="邮箱地址"  >
            <template #prefix>
              <el-icon><Message /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="code">
          <el-row :gutter="10" style="width: 100%">
            <el-col :span="17">
              <el-input v-model="form.code" type="text" minlength="6" maxlength="6" placeholder="请输入验证码" >
                <template #prefix>
                  <el-icon><EditPen /></el-icon>
                </template>
              </el-input>
            </el-col>
            <el-col :span="5">
              <el-button type="success" @click="validEmail" :disabled="!isEmailValid || cold>0">
                {{cold>0?"等待"+cold+"秒":"发送验证码"}}
              </el-button>
            </el-col>
          </el-row>
        </el-form-item>
      </el-form>

    </div>
      <div style="margin-top: 10px;">

      </div>
      <div style="margin-top: 80px">
        <el-button @click="register" style="width: 270px;" type="warning" plain>立即注册</el-button>
      </div>
    <div style="margin-top: 20px">
      <span style="font-size: 14px;line-height: 15px;color: gray">已有账号？</span>
      <el-link type="primary" style="translate: 0 -2px" @click="router.push('/')">立即登陆</el-link>
    </div>
  </div>
</template>

<script setup>
import {User,Lock,Message,EditPen} from '@element-plus/icons-vue'
import router from '../../router';
import {reactive,ref} from "vue";
import {post} from "../../net";
import {ElMessage} from "element-plus";


const form=reactive(
    {
      username:'',
      password:'',
      password_repeat:'',
      email:'',
      code:''
    }
)
const validateUsername=(rule,value,callback)=>{
  if(value===null){
    return callback(new Error('用户名不能为空'))
  }else if(!/^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(value)){
    return callback(new Error("用户名只能是数字和字母"))
  }else{
    callback();
  }
}
const validatePassword=(rule,value,callback)=>{
  if(value===null){
    return callback(new Error('不能为空'))
  }else if (value!==form.password){
    return callback(new Error('两次密码不一致'))
    }else{
    callback();
  }
}

const rules= {
  username:[
    { validator:validateUsername,trigger:['blur','change']},
    {min:3,max:8,message:'大小在3到8个字符之间 ',trigger: 'blur'}
  ],
  password:[
    {required:true,message:'密码不能为空，请输入密码',trigger:'blur'},
    {min:6,max:16,message:'密码长度为6到16个字符',trigger: 'blur'}
  ],
  password_repeat:[
    {required:true,message:'密码不能为空，请输入密码',trigger:'blur'},
    {validator:validatePassword,trigger:['blur','change']}
  ],
  email:[
    {required:true,message:'邮箱地址不能为空',trigger:['blur','change']},
    {type:'email',message:'请输入正确的邮箱地址',trigger: ['blur','change']}
  ],
  code:[
    {required:true,message:'请输入验证码',trigger:'blur'}
  ]
}
const isEmailValid=ref(false)
const formRef = ref()
const cold=ref(0)

const mailValid=(prop,isValid)=>{
  if(prop ==='email'){
    isEmailValid.value=isValid
  }else{
    isEmailValid.value=false
  }
}
const register=()=>{
  formRef.value.validate((isValid)=>{
    if(isValid){
      post("/api/auth/register",{
        username: form.username,
        password: form.password,
        email: form.email,
        code: form.code,
        withCredentials: true
      },(message)=>{ElMessage.success(message),router.push("/")},(message)=>{ElMessage.warning(message)})
    }else{
      ElMessage.warning('请完善未填写选项!');
    }
  })
}
const validEmail=()=>{
  post("/api/auth/valid-email",{
    email:form.email
  },(message)=>{
      ElMessage.success(message)
      cold.value=60
      setInterval(()=>cold.value--,1000)
    }
  )
}
</script>

<style scoped>

</style>
