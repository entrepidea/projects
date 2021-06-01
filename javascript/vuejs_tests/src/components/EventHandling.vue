<template>
  <div>
  <div id="basic-event">
    <button @click="counter += 1">Add 1</button>
    <p>The button above has been clicked {{ counter }} times.</p>
  </div>

  <div id="event-with-method">
    <!-- `greet` is the name of a method defined below -->
    <button @click="greet">Greet</button>
  </div>
  </div>

</template>

<script>
export default {
  name: "EventHandling",
  data(){
    return {
      counter: 1
    }
  },
  methods: {
    greet(event) {
      // `this` inside methods points to the current active instance
      alert('Hello ' + this.name + '!')
      // `event` is the native DOM event
      if (event) {
        alert(event.target.tagName)
      }
    }
  }
}
</script>

<style scoped>

</style>