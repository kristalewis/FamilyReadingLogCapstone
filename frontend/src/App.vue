<template>
  <div id="app">
    <div id="nav">
      <!-- listen for the event update-user and also pass the currentUser to the calling form -->
      <header-navigation @update-user="currentUser = getUser()" :currentUser="currentUser"/> 
    </div>
      <!-- listen for any events update-user and get the user -->
    <router-view @update-user="currentUser = getUser()" :currentUser="currentUser" />
  </div>
</template>

<script>
import auth from '@/auth.js';
import HeaderNavigation from '@/components/HeaderNavigation.vue'

export default {
  name: 'app',
  components: {
    HeaderNavigation
  },
  data() {
    return {
      currentUser: this.getUser()
    }
  },
  methods: {
    getUser() {
      return auth.getUser();
    }
  }
}
</script>
