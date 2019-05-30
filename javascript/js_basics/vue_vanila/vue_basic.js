
Vue.component("blog_post", {
    props:['post'],
    template: "<li>{{post.title}}</li>"
}
)

new Vue({
    el: '#app',
    data: {
        posts: [
            {title: "item1", id:1},
            {title: "item2",id:2},
            {title: "item3",id:3},
        ]
    }
})
