<template>
  <div style="width: 100vw;height: 100vh;overflow: hidden;display: flex;">
    <div style="flex: 1;">
      <el-image style="width: 100%;height: 100%;" fit="cover"
                src="https://img1.baidu.com/it/u=1532356346,1397797592&fm=253&fmt=auto&app=120&f=JPEG?w=1280&h=800"/>
    </div>
    <div class="welcome-title" style="">
      <div style="font-weight: bold;font-size: 30px;">欢迎来到学习平台</div>
      <div style="margin-top: 10px">这是一个Spring Boot+Vue3的项目，在这里可以随便测试各种功能。</div>
    </div>
    <div style="width: 400px;background-color:white;z-index:1">
      <router-view v-slot="{ Component }">
        <transition name="el-fade-in-linear" mode="out-in">
          <component :is="Component" style="height: 100%;"/>
        </transition>
      </router-view>
    </div>
  </div>
</template>

<script setup>

</script>

<style scoped>
  .welcome-title{
    left: 30px;
    bottom: 30px;
    position: absolute;
    color: white;
    text-shadow: 0 0 10px black;
  }
</style>
