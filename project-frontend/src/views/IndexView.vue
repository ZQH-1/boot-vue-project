<template>
  <div>登陆成功</div>
  <div>
    <el-button @click="logout()" type="danger" plain>退出登陆</el-button>
  </div>
</template>

<script setup>
import {get} from "../net";
import router from "../router";
import {ElMessage} from "element-plus";

const logout = () => {
  get("api/auth/logout",
      (message) => {
        ElMessage.success(message)
        router.push("/")
      })
}
</script>

<style scoped>

</style>
