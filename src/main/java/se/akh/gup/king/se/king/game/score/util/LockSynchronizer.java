package se.akh.gup.king.se.king.game.score.util;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

public class LockSynchronizer {


    private final Map<String, WeakReference<LockSynchronizer>> locker = Collections.synchronizedMap(new WeakHashMap<>());


    /***
     * Acquire lock with Weak reference which will be collected by GC once sync block completes.
     *
     * @param lockIdentifier            Lock Unique identifier
     * @return                          Weak reference of lock synchronizer.
     */
    public synchronized LockSynchronizer getLock(String lockIdentifier) {

        WeakReference<LockSynchronizer> lockWeakRef = locker.get(lockIdentifier);

        if (lockWeakRef == null || lockWeakRef.get() == null) {

            lockWeakRef = new WeakReference<>(new LockSynchronizer());
            locker.put(lockIdentifier, lockWeakRef);
        }
        return lockWeakRef.get();
    }
}
