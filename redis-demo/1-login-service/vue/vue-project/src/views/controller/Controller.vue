<script lang="ts">

    import axios from '../../axios/index';
    import Header from '../../components/Header.vue'

    export default {
        data() {
            return {

            };
        },
        mounted() {
            axios.get('test')
            .then(res => {
                console.log(res)
            })
        },
        methods: {

        },
        components: {
            Header
        }
    }
</script>

<template>
    <Header />
    <div>
        Controller
    </div>
</template>

<style scoped>

</style>
