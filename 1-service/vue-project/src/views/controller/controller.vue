<template>
    <div>
        <el-button type="primary" @click="router('/controller/user')">用户管理</el-button>
    </div>
</template>
  
<script type="text/ecmascript-6">


    export default {
        data() {
            return {

            };
        },
        methods: {
            router(uri) {
                this.$router.push(uri)
            }
        },
    };
</script>
  