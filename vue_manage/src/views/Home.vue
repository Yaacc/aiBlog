<template>
  <div>
    <h1>HELLO!{{ username }},欢迎来到主页</h1>
    <el-progress type="circle" :percentage="value1"></el-progress>
  </div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      reverse: true,
      value1: 90,
      username: "yaaa"
    };
  },
};
</script>
<style scoped>

</style>
