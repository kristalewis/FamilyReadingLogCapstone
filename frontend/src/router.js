import Vue from 'vue';
import Router from 'vue-router';
import auth from './auth';
import Home from './views/Home.vue';
import AwardedPrizes from './views/AwardedPrizes.vue';
import Login from './views/Login.vue';
import Register from './views/Register.vue';
// import AddBook from '@/views/AddBook.vue';
import MyBookshelf from '@/views/MyBookshelf.vue';
import ChildActivityLog from '@/views/ChildActivityLog.vue';
import ActivityLog from '@/views/ActivityLog.vue';
import Accounts from '@/views/Accounts.vue';
import RegisterFamily from './views/RegisterFamily.vue';
import Prizes from './views/Prizes.vue';
Vue.use(Router)

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/awardedprizes',
      name: 'awardedprizes',
      component: AwardedPrizes,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/login",
      name: "login",
      component: Login,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/register/:role?",
      name: "register",
      component: Register,
      meta: {
        requiresAuth: false
      }
    },
    // {
    //   path: '/addbook',
    //   name: 'addbook',
    //   component: AddBook,
    //   meta: {
    //     requiresAuth: true
    //   }
    // },
    {
      path: '/bookshelf',
      name: 'bookshelf',
      component: MyBookshelf,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/childactivity',
      name: 'childactivity',
      component: ChildActivityLog,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/activitylog',
      name: 'activitylog',
      component: ActivityLog,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/accounts',
      name: 'accounts',
      component: Accounts,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/registerfamily',
      name: 'registerfamily',
      component: RegisterFamily,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/prizes',
      name: 'prizes',
      component: Prizes,
      meta: {
        requiresAuth: true
      }
    }
  ]
})

router.beforeEach((to, from, next) => {
  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);
  const user = auth.getUser();

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && !user) {
    next("/login");
  } else {
    // Else let them go to their next destination
    next();
  }
});

export default router;
