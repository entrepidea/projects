<template>
<div id="two-way-binding">
  <p>{{ message }}</p>
  <input v-model="message"/>
</div>
</template>

<script>
export default {
  name: "TwoWayBinding",
  data(){
    return {message:"hello,Vue!"}
  }
}
</script>

<style scoped>

</style>