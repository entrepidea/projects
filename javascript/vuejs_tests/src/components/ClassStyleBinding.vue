<template>
  <div :style="styleObj">Vue manipulate my style!</div>
</template>

<script>
//Vue is capable of applying v-bind on the 'class' and 'style' attributes of a html tag
export default {
name: "ClassStyleBinding",
  data(){
    return {
      styleObj: {
        color: 'red',
        fontSize: '33px'
      }
    }
  }
}
</script>

<style scoped>

</style>