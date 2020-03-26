package com.coppel.rhconecta.dev.domain.common;


import com.coppel.rhconecta.dev.domain.common.failure.Failure;

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This abstraction represents an execution unit for different use cases (this means than any use
 * case in the application should implement this contract).
 * @param <Type>
 * @param <Params>
 */
public abstract class UseCase<Type, Params> {

    /**
     *
     * @param params
     * @return
     */
    public abstract void run(Params params, OnResultFunction<Either<Failure, Type>> callback);

    /**
     *
     *
     */
    public static class None {

        /* */
        private static None instance;

        /**
         *
         * @return
         */
        public static None getInstance() {
            if(instance == null)
                instance = new None();
            return instance;
        }

    }

    /**
     *
     * @param <Type>
     */
    public interface OnResultFunction<Type> {

        /**
         *
         * @param result
         */
        void onResult(Type result);

    }

}
