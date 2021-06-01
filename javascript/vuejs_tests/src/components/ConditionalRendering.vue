<template>
  <div id="conditional-rendering">
    <template v-if="seen"> <!-- using "template" can cover a bunch of elements rather than only one -->
      <h1>Test of conditional rendering...</h1>
      <span>Now you see me</span>
    </template>
    <p></p>
    <button v-on:click="toggle">Hide & Seek</button>
  </div>
</template>

<script>
export default {
  name: "ConditionalRendering",
  data(){
    return{seen:false}
  },
  methods:{
    toggle(){this.seen = !this.seen}
  }
}
</script>

<style scoped>

</style>