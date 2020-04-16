<template>
  <div id="accounts">
    <h3 class = "text-center">Family Members - {{this.family.familyname}}  </h3>
    <table class = "users table table-striped">
      <thead class="thead-dark">
      <tr>
        <th scope="col">#</th>
        <th scope="col">Username </th>
        <th scope="col">parent/child</th>
        <th scope="col"></th>
      </tr>
      </thead>
      <tbody>
        <tr class="users" v-for="user in users" v-bind:key="user.id">
          <td scope="row">{{user.id}}</td>
          <td>{{user.username}}</td>
          <td>{{user.role}}</td>
          <td>
            <b-button v-if="users.length === 1" variant="outline-danger" size="sm" @click="deleteUserId = user.id"
            v-b-modal.confirmDeleteFamily>Delete Family Account</b-button>
            <b-button v-else variant="outline-danger" size="sm" @click="deleteUserId = user.id"
            v-b-modal.confirmDelete> Delete</b-button>
          </td>
        </tr>
      </tbody>
    </table>
  <router-link
      :to="{ name: 'register', params: { role:'parent'} }"
      tag="button"
      class="btn btn-outline-primary btn-sm"
       >Add Parent</router-link>
   <router-link
      :to="{ name: 'register', params: { role:'child'} }"
        tag="button"
        class="btn btn-outline-primary btn-sm"
        >Add Child</router-link>
    <p class="text-right"> {{currentUser.familyId}}</p>
      <!-- Modal Component -->
  <b-modal
    id="confirmDelete"
    title="Delete User"
    ok-title="Delete User"
    @ok="deleteUser"
  >
    <p class="my-4">Are you sure you want to delete this User? (Warning: deleting this user will 
      delete all reading activity for this user.) </p>
  </b-modal>
  <b-modal
    id="confirmDeleteFamily"
    title="Delete Family Account"
    ok-title="Delete Family Account"
    @ok="deleteUser"
  >
    <p class="my-4">Are you sure you want to delete this Family Account? (Warning: deleting this family account will 
      delete all records associated with this family account.) </p>
  </b-modal>
  </div>
</template>

<script>

import auth from '@/auth';

export default {
  name: 'accounts',
   props: {
      currentUser: Object
    },
   data() {
     return {
        users: [],
        deleteUserId: null,
        family: ''
      }
    },
  methods: {
    getAllUsers() {
      fetch(`${process.env.VUE_APP_REMOTE_API}/api/users/${this.currentUser.familyId}`, {
        method: 'GET',
          headers: {
          Accept: 'application/json',
          Authorization: 'Bearer ' + auth.getToken()
          }
        })
        .then((response) => {
          return response.json()
        })
        .then((data) => {
          this.users = data;
        })
        .catch((err)=> console.error(err)); 
    },
   deleteUser() {
      fetch(`${process.env.VUE_APP_REMOTE_API}/api/user/${this.deleteUserId}`, {
        method: 'DELETE',
        headers: {
          Authorization: 'Bearer ' + auth.getToken(),
        },
        credentials: 'same-origin',
      })
        .then((response) => {
          if (response.ok) {
            const index = this.users.map((user) => user.id).indexOf(this.deleteUserId);
            this.users.splice(index, 1);
          }
          this.deleteUserId = null;
          this.getAllUsers();
        })
        .catch((err) => console.error(err));
        if (this.users.length === 1) {
          auth.logout();
          this.$emit('update-user')
          this.$router.push('/login');
        }
      },
     getfamily() {
      fetch(`${process.env.VUE_APP_REMOTE_API}/api/families/${this.currentUser.familyId}`, {
        method: 'GET',
        headers: {
          Authorization: 'Bearer ' + auth.getToken(),
        },
        credentials: 'same-origin',
        })
        .then((response) => {
          return response.json()
        })
        .then((data) => {
          this.family = data;
        })
        .catch((err)=> console.error(err)); 
      }
    },
    created() {
    this.getAllUsers();
    this.getfamily();
  }
}
</script>

<style scoped>
  .bold {
    font-weight: bold;
  }
  button {
    margin-right: 5px;
  }
  span.badge {
    margin-right:5px;
  }
  .actions .btn{
    margin-top: 0px;
  }

</style>