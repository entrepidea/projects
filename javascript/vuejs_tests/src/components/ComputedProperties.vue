<template>
  <div id="computed-basics">
    <p>Has published books:</p>
    <span>{{ publishedBooksMessage }}</span>

    <div id = "computed_props2">
      Full name: <input type = "text" v-model = "fullname" /> <br/><br/>
      <h1>My name is {{firstname}} {{lastname}}</h1>
    </div>

  </div>
</template>

<script>
//computed properties are clearer than using an expression in template's {{ }}. It's also easier to manager
//https://v3.vuejs.org/guide/computed.html#computed-properties
export default {
  name: "ComputedProperties",
  data(){
    return {
      author: {
        name: 'John Doe',
        books: [
          'Vue 2 - Advanced Guide',
          'Vue 3 - Basic Guide',
          'Vue 4 - The Mystery'
        ]
      },
      firstname : 'John',
      lastname: 'Doe'
    }
  },
  computed: {
    //a computed getter
    publishedBooksMessage(){
      return this.author.books.length > 0? 'YES':'NO'
    },
    //example showing how to use set in a computed properties (from: https://www.tutorialspoint.com/vuejs/vuejs_computed_properties.htm)
    fullname:{
      get(){
        return this.firstname + " "+this.lastname
      },
      set(newVal){
        const names = newVal.split(" ")
        this.firstname = names[0]
        this.lastname = names[names.length - 1]
      }
    }

  }
}
</script>

<style scoped>

</style>